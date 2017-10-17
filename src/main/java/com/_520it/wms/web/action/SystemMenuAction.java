package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.ISystemMenuService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class SystemMenuAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ISystemMenuService systemMenuService;
	@Getter
	private SystemMenu systemMenu = new SystemMenu();
	@Getter
	private SystemMenuQueryObject qo=new SystemMenuQueryObject();

	// 查询
	@RequiredPermission("系统菜单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("result", systemMenuService.query(qo));
		List<Map<String, Object>> menus = systemMenuService.selectMenus(qo.getParentId());
		putContext("menus",menus);
		return LIST;
	}

	// 删除
	@RequiredPermission("系统菜单删除")
	public String delete() {
		try {
			if (systemMenu.getId() != null) {
                systemMenuService.delete(systemMenu.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("系统菜单编辑")
	public String input() throws Exception {
		if(qo.getParentId()>0){
			//根据parentId去获取当前这级的父菜单
			SystemMenu parent=systemMenuService.get(qo.getParentId());
			putContext("parentName",parent.getName());
		}else{
			putContext("parentName","根目录");
		}
		if (systemMenu.getId() != null) {
			systemMenu = systemMenuService.get(systemMenu.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("系统菜单保存或更新")
	public String saveOrUpdate() {
		try {
			if(qo.getParentId()>0){
				SystemMenu parent=systemMenuService.get(qo.getParentId());
				systemMenu.setParent(parent);//有父级菜单的目录
			}else{
				systemMenu.setParent(null);//根目录下的菜单
			}
			if (systemMenu.getId() != null) {
                systemMenuService.update(systemMenu);
                addActionMessage("更新成功");
            } else {
                systemMenuService.save(systemMenu);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	public String queryMenusBySn() throws Exception{
		List<Map<String,Object>> menus = systemMenuService.queryMenusBySn(qo.getParentSn());
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(JSON.toJSONString(menus));
		return NONE;
	}

}
