package com._520it.wms.web.action;

import com._520it.wms.domain.Department;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private IDepartmentService departmentService;
	@Getter
	private Department department = new Department();
	@Getter
	private QueryObject qo=new QueryObject();

	// 查询
	@RequiredPermission("部门列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
//		putContext("result", departmentService.query(qo));
		PageResult result = departmentService.query(qo);
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(JSON.toJSONString(result.getListData()));
		System.out.println(JSON.toJSONString(result));
		return NONE;
	}

	// 删除
	@RequiredPermission("部门删除")
	public String delete() {
		try {
			if (department.getId() != null) {
                departmentService.delete(department.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("部门编辑")
	public String input() throws Exception {
		if (department.getId() != null) {
			department = departmentService.get(department.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("部门保存或更新")
	public String saveOrUpdate() {
		try {
			if (department.getId() != null) {
                departmentService.update(department);
                addActionMessage("更新成功");
            } else {
                departmentService.save(department);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
}
