package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockIncomeBillItem;
import com._520it.wms.mapper.StockIncomeBillMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.service.IStockIncomeBillService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService {

    @Autowired
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Autowired
    private IProductStockService productStockService;

    @Override
    public void save(StockIncomeBill stockIncomeBill) {
        // 1. 录入人,录入时间,状态
        stockIncomeBill.setInputTime(new Date());
        stockIncomeBill.setInputUser(UserContext.getCurrentUser());
        stockIncomeBill.setStatus(StockIncomeBill.NORMAL);
        // 2. 遍历菜单明细,计算总数量和总金额
        List<StockIncomeBillItem> items = stockIncomeBill.getItems();
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (StockIncomeBillItem item : items) {
            BigDecimal number = item.getNumber();
            BigDecimal costPrice = item.getCostPrice();
            BigDecimal amount = number.multiply(costPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        stockIncomeBill.setTotalAmount(totalAmount);
        stockIncomeBill.setTotalNumber(totalNumber);
        // 3. 保存采购订单,获取到billid
        stockIncomeBillMapper.insert(stockIncomeBill);
        // 4. 为订单明细设定billid,保存订单明细
        for (StockIncomeBillItem item : items) {
            item.setBill(stockIncomeBill);
            stockIncomeBillMapper.insertItem(item);
        }
    }

    @Override
    public void delete(long id) {
        stockIncomeBillMapper.deleteItemByBillId(id);
        stockIncomeBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(StockIncomeBill stockIncomeBill) {
        // 1. 删除订单明细
        stockIncomeBillMapper.deleteItemByBillId(stockIncomeBill.getId());
        // 2. 遍历菜单明细,计算总数量和总金额
        List<StockIncomeBillItem> items = stockIncomeBill.getItems();
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (StockIncomeBillItem item : items) {
            BigDecimal number = item.getNumber();
            BigDecimal costPrice = item.getCostPrice();
            BigDecimal amount = number.multiply(costPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
        // 4. 为订单明细设定billid,保存订单明细
            item.setBill(stockIncomeBill);
            stockIncomeBillMapper.insertItem(item);
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        stockIncomeBill.setTotalAmount(totalAmount);
        stockIncomeBill.setTotalNumber(totalNumber);
        stockIncomeBillMapper.updateByPrimaryKey(stockIncomeBill);
    }

    @Override
    public StockIncomeBill get(long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(QueryObject qo) {
        int count = stockIncomeBillMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<StockIncomeBill> listData = stockIncomeBillMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void audit(Long stockIncomeBillId) {
        try {
            StockIncomeBill stockIncomeBill = stockIncomeBillMapper.selectByPrimaryKey(stockIncomeBillId);
            if (stockIncomeBill.getStatus()==StockIncomeBill.AUDIT){
                throw new Exception("该订单已经经过审核,不能在进行审核!");
            }
            // 1. 设定审核人,审核时间,审核状态
            stockIncomeBill.setAuditor(UserContext.getCurrentUser());
            stockIncomeBill.setAuditTime(new Date());
            stockIncomeBill.setStatus(StockIncomeBill.AUDIT);
            // 2. 修改库存
            Depot depot = stockIncomeBill.getDepot();
            List<StockIncomeBillItem> items = stockIncomeBill.getItems();
            for (StockIncomeBillItem item : items) {
                productStockService.income(item.getProduct(),depot,item.getCostPrice(),item.getNumber());
            }
            stockIncomeBillMapper.audit(stockIncomeBill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
