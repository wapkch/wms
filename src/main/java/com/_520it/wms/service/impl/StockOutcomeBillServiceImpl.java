package com._520it.wms.service.impl;

import com._520it.wms.domain.*;
import com._520it.wms.mapper.SaleAccountMapper;
import com._520it.wms.mapper.StockOutcomeBillMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.StockOutComeBillQueryObject;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.service.IStockOutcomeBillService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {

    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Autowired
    private IProductStockService productStockService;
    @Autowired
    private SaleAccountMapper saleAccountMapper;

    @Override
    public void save(StockOutcomeBill stockOutcomeBill) {
        // 1. 录入人,录入时间,状态
        stockOutcomeBill.setInputTime(new Date());
        stockOutcomeBill.setInputUser(UserContext.getCurrentUser());
        stockOutcomeBill.setStatus(StockOutcomeBill.NORMAL);
        // 2. 遍历菜单明细,计算总数量和总金额
        List<StockOutcomeBillItem> items = stockOutcomeBill.getItems();
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (StockOutcomeBillItem item : items) {
            BigDecimal number = item.getNumber();
            BigDecimal salePrice = item.getSalePrice();
            BigDecimal amount = number.multiply(salePrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        stockOutcomeBill.setTotalAmount(totalAmount);
        stockOutcomeBill.setTotalNumber(totalNumber);
        // 3. 保存采购订单,获取到billid
        stockOutcomeBillMapper.insert(stockOutcomeBill);
        // 4. 为订单明细设定billid,保存订单明细
        for (StockOutcomeBillItem item : items) {
            item.setBill(stockOutcomeBill);
            stockOutcomeBillMapper.insertItem(item);
        }
    }

    @Override
    public void delete(long id) {
        stockOutcomeBillMapper.deleteItemByBillId(id);
        stockOutcomeBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(StockOutcomeBill stockOutcomeBill) {
        // 1. 删除订单明细
        stockOutcomeBillMapper.deleteItemByBillId(stockOutcomeBill.getId());
        // 2. 遍历菜单明细,计算总数量和总金额
        List<StockOutcomeBillItem> items = stockOutcomeBill.getItems();
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (StockOutcomeBillItem item : items) {
            BigDecimal number = item.getNumber();
            BigDecimal salePrice = item.getSalePrice();
            BigDecimal amount = number.multiply(salePrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
        // 4. 为订单明细设定billid,保存订单明细
            item.setBill(stockOutcomeBill);
            stockOutcomeBillMapper.insertItem(item);
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        stockOutcomeBill.setTotalAmount(totalAmount);
        stockOutcomeBill.setTotalNumber(totalNumber);
        stockOutcomeBillMapper.updateByPrimaryKey(stockOutcomeBill);
    }

    @Override
    public StockOutcomeBill get(long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(StockOutComeBillQueryObject qo) {
        int count = stockOutcomeBillMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<StockOutcomeBill> listData = stockOutcomeBillMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void audit(Long stockOutcomeBillId) {
        try {
            StockOutcomeBill stockOutcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(stockOutcomeBillId);
            if (stockOutcomeBill.getStatus()==StockOutcomeBill.AUDIT){
                throw new RuntimeException("该订单已经经过审核,不能在进行审核!");
            }
            // 1. 设定审核人,审核时间,审核状态
            stockOutcomeBill.setAuditor(UserContext.getCurrentUser());
            stockOutcomeBill.setAuditTime(new Date());
            stockOutcomeBill.setStatus(StockOutcomeBill.AUDIT);
            // 2. 修改库存
            Depot depot = stockOutcomeBill.getDepot();
            Date vdate = stockOutcomeBill.getVdate();
            Client client = stockOutcomeBill.getClient();
            Employee saleman = stockOutcomeBill.getInputUser();
            List<StockOutcomeBillItem> items = stockOutcomeBill.getItems();
            for (StockOutcomeBillItem item : items) {
                BigDecimal costPrice = productStockService.outcome(item.getProduct(), depot, item.getNumber());
                // 向销售帐中添加记录
                SaleAccount sc = new SaleAccount();
                sc.setVdate(vdate);
                sc.setProduct(item.getProduct());
                sc.setClient(client);
                sc.setNumber(item.getNumber());
                sc.setSaleman(saleman);
                sc.setCostPrice(costPrice);
                sc.setCostAmount(costPrice.multiply(item.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
                sc.setSalePrice(item.getSalePrice());
                sc.setSaleAmount(item.getSalePrice().multiply(item.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
                saleAccountMapper.insert(sc);
            }
            stockOutcomeBillMapper.audit(stockOutcomeBill);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
