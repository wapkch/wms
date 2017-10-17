package com._520it.wms.service;

import com._520it.wms.domain.Department;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IDepartmentService {
	void save(Department dept);

	void delete(long id);

	void update(Department dept);

	Department get(long id);

	List<Department> listAll();

	PageResult query(QueryObject qo);

}
