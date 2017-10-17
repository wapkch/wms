package com._520it.wms.mapper;

import com._520it.wms.domain.Department;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {

	void save(Department dept);

	void delete(long id);

	void update(Department dept);

	Department get(long id);

	List<Department> listAll();

	int queryForCount(QueryObject qo);

	List<Department> queryForList(QueryObject qo);
}
