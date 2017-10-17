package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//销售出库单明细
@Setter
@Getter
public class StockOutcomeBillItem extends BaseDomain {
    //价格
    private BigDecimal salePrice;
    //数量
    private BigDecimal number;
    //金额小计
    private BigDecimal amount;
    //备注说明
    private String remark;
    //商品信息
    private Product product;
    //关联出库单
    private StockOutcomeBill bill;
}
