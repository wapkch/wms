package com._520it.wms.mapper;

import com._520it.wms.domain.Client;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Client record);

    Client selectByPrimaryKey(Long id);

    List<Client> selectAll();

    int updateByPrimaryKey(Client record);

    int queryForCount(QueryObject qo);

    List<Client> queryForList(QueryObject qo);

}