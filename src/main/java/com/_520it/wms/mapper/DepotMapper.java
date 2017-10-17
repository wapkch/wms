package com._520it.wms.mapper;

import com._520it.wms.domain.Depot;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot record);

    int queryForCount(QueryObject qo);

    List<Depot> queryForList(QueryObject qo);
}