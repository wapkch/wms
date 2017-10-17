package com._520it.wms.web.action;

import com._520it.wms.domain.Department;
import com._520it.wms.domain.Employee;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.Role;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.query.PageResult;
import com._520it.wms.service.IDepartmentService;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.service.IRoleService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Scope("prototype")
public class EmployeeAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService deptService;
    @Autowired
    private IRoleService roleService;
    @Getter
    private Employee employee = new Employee();
    @Getter
    private EmployeeQueryObject qo = new EmployeeQueryObject();
    @Setter
    private List<Long> ids = new ArrayList<>();
    @Setter
    private int page;
    @Setter
    private int rows;

    // 查询
    @RequiredPermission("员工列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {
        //模拟异常
        try {
            qo.setCurrentPage(page);
            qo.setPageSize(rows);
            System.out.println(qo.getKeyword());
            PageResult result = employeeService.query(qo);
            putContext("result", result);

            Integer totalCount = result.getTotalCount();
            List<Employee> listData = (List<Employee>) result.getListData();
            HashMap map = new HashMap();
            map.put("total",totalCount);
            map.put("rows",listData==null?new ArrayList<>():listData);
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().write(JSON.toJSONString(map));
            System.out.println(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return NONE;
    }

    // 删除
    @RequiredPermission("员工删除")
    public String delete() {
        try {
            if (employee.getId() != null) {
                employeeService.delete(employee.getId());
                putMsg("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败:" + e.getMessage());
        }
        return NONE;
    }

    @RequiredPermission("员工批量删除")
    public String batchDelete() {
        try {
            System.out.println(ids);
            employeeService.batchDelete(ids);
            putMsg("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("批量删除失败:" + e.getMessage());
        }
        return NONE;
    }


    // 页面跳转
    @RequiredPermission("员工编辑")
    public String input() throws Exception {
        // 查询出所有的角色信息
        List<Role> roles = roleService.listAll();
        putContext("roles", roles);
        // 查询出所有的部门信息
        List<Department> depts = deptService.listAll();
        putContext("depts", depts);
        if (employee.getId() != null) {
            employee = employeeService.get(employee.getId());
        }
        return INPUT;
    }

    // 保存或者更新
    @RequiredPermission("员工保存或更新")
    //在进入saveOrUpdate方,如果有异常信息,那么就会往指定的input方法去跳
    public String saveOrUpdate() throws Exception {
        try {
            if (employee.getId() != null) {
                employeeService.update(employee);
//                addActionMessage("修改成功");
                putMsg("修改成功");
            } else {
                employeeService.save(employee);
//                addActionMessage("新增成功");
                putMsg("新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
//            addActionError(e.getMessage());
            putMsg("操作失败");
        }
        return NONE;
    }
}
