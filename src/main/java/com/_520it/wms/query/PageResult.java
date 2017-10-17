package com._520it.wms.query;

import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

//分页结果数据的封装:为页面显示做准备
@Getter
@ToString
public class PageResult {

	private List<?> listData;
	private Integer totalCount;
	private Integer currentPage;
	private Integer pageSize;
	private Integer totalPage;
	private Integer prevPage;
	private Integer nextPage;
	public PageResult(){}
	public PageResult(List<?> listData, Integer totalCount, Integer currentPage, Integer pageSize) {
		super();
		this.listData = listData;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;

		this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize
				: this.totalCount / this.pageSize + 1;
		this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
		this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1 : this.totalPage;
	}

	// 获取到一个空的结果对象
	public   PageResult emptyResult(int pageSize) {
		return new PageResult(Collections.EMPTY_LIST, 0, 1, pageSize);
	}

}
