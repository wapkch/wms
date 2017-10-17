package com._520it.wms.service;

import com._520it.wms.domain.Brand;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IBrandService {
	void save(Brand brand);

	void delete(long id);

	void update(Brand brand);

	Brand get(long id);

	List<Brand> listAll();

	PageResult query(QueryObject qo);

}
