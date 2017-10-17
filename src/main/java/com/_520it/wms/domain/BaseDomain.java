package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//把各个实体中的相同数据封装到通用的实体中
@Getter
@Setter
public class BaseDomain implements Serializable {
	private static final long serialVersionUID = 2335785733304630024L;
	private Long id;
	
}
