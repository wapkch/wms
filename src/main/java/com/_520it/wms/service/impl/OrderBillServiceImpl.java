package com._520it.wms.service.impl;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.mapper.OrderBillMapper;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.query.PageResult;
import com._520it.wms.service.IOrderBillService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper orderBillMapper;

    @Override
    public void save(OrderBill orderBill) {
        // 1. 录入人,录入时间,状态
        orderBill.setInputTime(new Date());
        orderBill.setInputUser(UserContext.getCurrentUser());
        orderBill.setStatus(OrderBill.NORMAL);
        // 2. 遍历菜单明细,计算总数量和总金额
        List<OrderBillItem> items = orderBill.getItems();
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderBillItem item : items) {
            BigDecimal number = item.getNumber();
            BigDecimal costPrice = item.getCostPrice();
            BigDecimal amount = number.multiply(costPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);
        // 3. 保存采购订单,获取到billid
        orderBillMapper.insert(orderBill);
        // 4. 为订单明细设定billid,保存订单明细
        for (OrderBillItem item : items) {
            item.setBill(orderBill);
            orderBillMapper.insertItem(item);
        }
    }

    @Override
    public void delete(long id) {
        orderBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderBill orderBill) {
        // 1. 删除订单明细
        orderBillMapper.deleteItemByBillId(orderBill.getId());
        // 2. 遍历菜单明细,计算总数量和总金额
        List<OrderBillItem> items = orderBill.getItems();
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderBillItem item : items) {
            BigDecimal number = item.getNumber();
            BigDecimal costPrice = item.getCostPrice();
            BigDecimal amount = number.multiply(costPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
        // 4. 为订单明细设定billid,保存订单明细
            item.setBill(orderBill);
            orderBillMapper.insertItem(item);
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);
        orderBillMapper.updateByPrimaryKey(orderBill);
    }

    @Override
    public OrderBill get(long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(OrderBillQueryObject qo) {
        int count = orderBillMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<OrderBill> listData = orderBillMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void audit(Long orderBillId) {
        try {
            OrderBill orderBill = orderBillMapper.selectByPrimaryKey(orderBillId);
            if (orderBill.getStatus()==OrderBill.AUDIT){
                throw new Exception("该订单已经经过审核,不能在进行审核!");
            }
            // 1. 设定审核人,审核时间,审核状态
            orderBill.setAuditor(UserContext.getCurrentUser());
            orderBill.setAuditTime(new Date());
            orderBill.setStatus(OrderBill.AUDIT);
            orderBillMapper.audit(orderBill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
