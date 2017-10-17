package com._520it.wms.mapper;

import com._520it.wms.domain.Product;
import com._520it.wms.query.ProductQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Product record);

    int queryForCount(ProductQueryObject qo);

    List<Product> queryForList(ProductQueryObject qo);
}