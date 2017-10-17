package com._520it.wms.mapper;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    int queryForCount(EmployeeQueryObject qo);

    List<Employee> queryForList(EmployeeQueryObject qo);

    Employee checkLogin(@Param("username") String username, @Param("password") String password);

    void deleteRelation(Long employeeId);

    void saveRelations(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    void batchDelete(List<Long> ids);

    void batchDeleteRelation(List<Long> ids);
}