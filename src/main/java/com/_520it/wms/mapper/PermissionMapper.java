package com._520it.wms.mapper;

import com._520it.wms.domain.Permission;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {

	void save(Permission permission);

	void delete(long id);

	List<Permission> listAll();

	int queryForCount(QueryObject qo);

	List<Permission> queryForList(QueryObject qo);
}
