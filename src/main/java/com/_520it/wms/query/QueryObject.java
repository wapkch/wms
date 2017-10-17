package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {
	private Integer pageSize=5;
	private Integer currentPage=1;

	public int getBeginIndex() {
		return (currentPage - 1) * pageSize;
	}
}
