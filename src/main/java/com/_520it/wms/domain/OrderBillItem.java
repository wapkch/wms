package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//采购订单明细
@Setter
@Getter
public class OrderBillItem extends BaseDomain {
    //价格
    private BigDecimal costPrice;
    //数量
    private BigDecimal number;
    //金额小计
    private BigDecimal amount;
    //备注说明
    private String remark;
    //商品信息
    private Product product;
    //关联采购订单
    private OrderBill bill;
}
