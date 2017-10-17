package com._520it.wms.mapper;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    int queryForCount(QueryObject qo);

    List<SystemMenu> queryForList(QueryObject qo);

    List<SystemMenu> listSonMenus();

    List<Map<String,Object>> queryMenusBySn(String parentSn);
}