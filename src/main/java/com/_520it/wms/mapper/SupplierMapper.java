package com._520it.wms.mapper;

import com._520it.wms.domain.Supplier;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier record);

    int queryForCount(QueryObject qo);

    List<Supplier> queryForList(QueryObject qo);

}