package com._520it.wms.service;

import com._520it.wms.domain.Permission;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IPermissionService {
	void save(Permission permission);

	void delete(long id);

	List<Permission> listAll();

	PageResult query(QueryObject qo);

	/**
	 * 加载权限
	 */
	void reload();
}
