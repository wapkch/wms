package com._520it.wms.web.action;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.Role;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.service.IRoleService;
import com._520it.wms.service.ISystemMenuService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPermissionService permissionService;
	@Autowired
	private ISystemMenuService systemMenuService;
	@Getter
	private Role role = new Role();
	@Getter
	private QueryObject qo = new QueryObject();

	// 查询
	@InputConfig(methodName = "input")
	@RequiredPermission("角色列表")
	public String execute() throws Exception {
		PageResult result = roleService.query(qo);
		putContext("result", result);
		return LIST;
	}

	// 删除
	@RequiredPermission("角色删除")
	public String delete()  {
		try {
			if (role.getId() != null) {
                roleService.delete(role.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());
		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("角色编辑")
	public String input() throws Exception {
		// 查询出所有的权限信息
		List<Permission> permissions = permissionService.listAll();
		putContext("permissions", permissions);
		putContext("menus",systemMenuService.listSonMenus());
		if (role.getId() != null) {
			role = roleService.get(role.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("角色保存或更新")
	public String saveOrUpdate() throws Exception {
		try {
			if (role.getId() != null) {
                roleService.update(role);
				addActionMessage("更新成功");
            } else {
                roleService.save(role);
				addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("保存失败:"+e.getMessage());
		}
		return SUCCESS;
	}
}
