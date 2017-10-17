package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission extends BaseDomain {

	private static final long serialVersionUID = -8577705573181882414L;

	private String name;
	// com._520it.wms.web.action.DepartmentAction:delete
	private String expression;// 权限表达式

	@Override
	public String toString() {
		return "Permission [name=" + name + ", expression=" + expression + "]";
	}

}
