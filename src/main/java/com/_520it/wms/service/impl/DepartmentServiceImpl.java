package com._520it.wms.service.impl;

import com._520it.wms.domain.Department;
import com._520it.wms.mapper.DepartmentMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department dept) {
        departmentMapper.save(dept);
    }

    @Override
    public void delete(long id) {
        departmentMapper.delete(id);
    }

    @Override
    public void update(Department dept) {
        departmentMapper.update(dept);
    }

    @Override
    public Department get(long id) {
        return departmentMapper.get(id);
    }

    @Override
    public List<Department> listAll() {
        return departmentMapper.listAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = departmentMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Department> listData = departmentMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
