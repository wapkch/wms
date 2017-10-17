package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department extends BaseDomain {

	private static final long serialVersionUID = -5592346168118253717L;
	private String name;
	private String sn;

	@Override
	public String toString() {
		return "Department [name=" + name + ", sn=" + sn + "]";
	}

}
