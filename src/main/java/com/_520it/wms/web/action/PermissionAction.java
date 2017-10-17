package com._520it.wms.web.action;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IPermissionService permissionService;
	@Getter
	private Permission permission = new Permission();
	@Getter
	private QueryObject qo = new QueryObject();

	// 查询
	@RequiredPermission("权限列表")
	public String execute() throws Exception {
		PageResult result = permissionService.query(qo);
		putContext("result", result);
		return LIST;
	}

	// 删除
	@RequiredPermission("权限删除")
	public String delete()  {
		try {
			//int i=1/0;
			if (permission.getId() != null) {
                permissionService.delete(permission.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());
		}
		return NONE;
	}

	public String reload(){
		try {
			permissionService.reload();
			putMsg("加载成功");
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("加载失败:"+e.getMessage());
		}
		return NONE;
	}

}
