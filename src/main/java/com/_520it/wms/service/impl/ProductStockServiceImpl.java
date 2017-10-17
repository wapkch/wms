package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.Product;
import com._520it.wms.domain.ProductStock;
import com._520it.wms.mapper.ProductStockMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductStockQueryObject;
import com._520it.wms.service.IProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductStockServiceImpl implements IProductStockService {

    @Autowired
    private ProductStockMapper productStockMapper;

    @Override
    public void income(Product product, Depot depot, BigDecimal costPrice, BigDecimal number) {
        ProductStock ps = productStockMapper.selectByProductIdAndDepotId(product.getId(),depot.getId());
        if (ps==null){
            // 不存在改库存
            ps = new ProductStock();
            ps.setPrice(costPrice);
            ps.setStoreNumber(number);
            ps.setAmount(ps.getPrice().multiply(ps.getStoreNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
            ps.setDepot(depot);
            ps.setProduct(product);
            productStockMapper.insert(ps);
        }else {
            // 改仓库中存在改商品,
            ps.setStoreNumber(ps.getStoreNumber().add(number).setScale(2,BigDecimal.ROUND_HALF_UP));
            ps.setAmount(ps.getAmount().add(costPrice.multiply(number)).setScale(2,BigDecimal.ROUND_HALF_UP));
            ps.setPrice(ps.getAmount().divide(ps.getStoreNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
            productStockMapper.updateByPrimaryKey(ps);
        }
    }

    @Override
    public BigDecimal outcome(Product product, Depot depot, BigDecimal number) {
        ProductStock ps = productStockMapper.selectByProductIdAndDepotId(product.getId(),depot.getId());
        if (ps==null){
            // 不存在改库存
            throw new RuntimeException(product.getName()+"商品没有库存了!");
        }
        if (ps.getStoreNumber().compareTo(number)==-1){
            // 库存不够
            throw new RuntimeException(product.getName()+"库存不够,只有"+ps.getStoreNumber()+"库存了");
        }
        ps.setStoreNumber(ps.getStoreNumber().subtract(number).setScale(2,BigDecimal.ROUND_HALF_UP));
        ps.setAmount(ps.getPrice().multiply(ps.getStoreNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
        productStockMapper.updateByPrimaryKey(ps);
        return ps.getPrice();
    }

    @Override
    public PageResult query(ProductStockQueryObject qo) {
        int count = productStockMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<ProductStock> listData = productStockMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
