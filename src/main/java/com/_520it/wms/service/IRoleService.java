package com._520it.wms.service;

import com._520it.wms.domain.Role;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IRoleService {
	void save(Role role);

	void delete(long id);

	void update(Role role);

	Role get(long id);

	List<Role> listAll();

	/**
	 * 高级查询
	 * 
	 * @param qo
	 * @return
	 */
	PageResult query(QueryObject qo);
}
