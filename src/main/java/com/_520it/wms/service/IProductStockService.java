package com._520it.wms.service;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.Product;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductStockQueryObject;

import java.math.BigDecimal;

public interface IProductStockService {

    //入库

    /**
     *
     * @param product 商品信息
     * @param depot   仓库信息
     * @param costPrice  采购价格
     * @param number   入库数量
     */
    void income(Product product, Depot depot, BigDecimal costPrice, BigDecimal number);
    //出库

    /**
     *  @param product 商品信息
     * @param depot 仓库信息
     * @param number 出库数量
     */
    BigDecimal outcome(Product product, Depot depot, BigDecimal number);

    PageResult query(ProductStockQueryObject qo);
}
