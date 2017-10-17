package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Employee extends BaseDomain {
	private static final long serialVersionUID = -1451687007793836651L;
	private String name;
	private String password;
	private String email;
	private Integer age;
	private boolean admin;// 是否是超级管理员
	// 多对一
	private Department dept;
	//当前员工所拥有的角色信息, 保存在中间表
	private List<Role> roles = new ArrayList<>();

	@Override
	public String toString() {
		return "Employee [name=" + name + ", password=" + password + ", email=" + email + ", age=" + age + ", admin="
				+ admin + "]";
	}

}
