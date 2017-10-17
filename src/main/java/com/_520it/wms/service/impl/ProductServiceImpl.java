package com._520it.wms.service.impl;

import com._520it.wms.domain.Product;
import com._520it.wms.mapper.ProductMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void save(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public Product get(long id) {
        return productMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(ProductQueryObject qo) {
        int count = productMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Product> listData = productMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
