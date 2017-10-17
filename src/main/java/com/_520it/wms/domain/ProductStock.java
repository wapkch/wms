package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/9/22.
 */
//库存信息
@Setter@Getter
public class ProductStock extends  BaseDomain {
    //库存价格 移动加权平均计算
    private BigDecimal price;
    //库存数量
    private BigDecimal storeNumber;
    //库存总金额
    private BigDecimal amount;
    //商品信息
    private Product product;
    //仓库信息 : 通过仓库+商品在ProductStock只能找到一个唯一的记录
    private Depot depot;
}
