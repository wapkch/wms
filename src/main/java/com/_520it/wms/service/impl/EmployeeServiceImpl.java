package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.Role;
import com._520it.wms.mapper.EmployeeMapper;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.query.PageResult;
import com._520it.wms.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Employee employee) {
        employeeMapper.insert(employee);
        List<Role> roles = employee.getRoles();
        for (Role role : roles) {
            employeeMapper.saveRelations(employee.getId(),role.getId());
        }

    }

    @Override
    public void delete(long id) {
        employeeMapper.deleteRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.updateByPrimaryKey(employee);
        employeeMapper.deleteRelation(employee.getId());
        List<Role> roles = employee.getRoles();
        for (Role role : roles) {
            employeeMapper.saveRelations(employee.getId(),role.getId());
        }
    }

    @Override
    public Employee get(long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> listAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public PageResult query(EmployeeQueryObject qo) {
        int count = employeeMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Employee> listData = employeeMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public Employee checkLogin(String username, String password) {
        return employeeMapper.checkLogin(username,password);
    }

    @Override
    public void batchDelete(List<Long> ids) {
        try {
            employeeMapper.batchDeleteRelation(ids);
            employeeMapper.batchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
