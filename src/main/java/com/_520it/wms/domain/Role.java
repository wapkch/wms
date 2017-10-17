package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Role extends BaseDomain {

	private static final long serialVersionUID = -8579073329083400962L;

	private String name;
	private String sn;
	// 保存当前角色所拥有的权限:单向多对多
	private List<Permission> permissions = new ArrayList<>();
	//当前角色所拥有的菜单: 单向多对多
	private List<SystemMenu> menus=new ArrayList<>();

	@Override
	public String toString() {
		return "Role [name=" + name + ", sn=" + sn + "]";
	}

}
