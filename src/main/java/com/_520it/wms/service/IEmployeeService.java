package com._520it.wms.service;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.query.PageResult;

import java.util.List;

public interface IEmployeeService {
	void save(Employee employee);

	void delete(long id);

	void update(Employee employee);

	Employee get(long id);

	List<Employee> listAll();

	PageResult query(EmployeeQueryObject qo);

	Employee checkLogin(String username, String password);

    void batchDelete(List<Long> ids);
}
