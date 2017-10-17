package com._520it.wms.mapper;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.query.ProductStockQueryObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductStock record);

    ProductStock selectByPrimaryKey(Long id);

    List<ProductStock> selectAll();

    int updateByPrimaryKey(ProductStock record);

    ProductStock selectByProductIdAndDepotId(@Param("productId") Long productId, @Param("depotId") Long depotId);

    int queryForCount(ProductStockQueryObject qo);

    List<ProductStock> queryForList(ProductStockQueryObject qo);
}