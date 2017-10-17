package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

//销售账
@Setter@Getter
public class SaleAccount extends  BaseDomain {

    //销售日期
    private Date vdate;
    //销售数量
    private BigDecimal number;
    //成本价格
    private BigDecimal costPrice;
    //成本总金额
    private BigDecimal costAmount;
    //销售价格
    private BigDecimal salePrice;
    //销售总金额
    private BigDecimal saleAmount;
    //产品
    private Product product;
    //销售人员
    private Employee saleman;
    //客户
    private Client client;
}
