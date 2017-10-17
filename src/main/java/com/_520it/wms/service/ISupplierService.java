package com._520it.wms.service;

import com._520it.wms.domain.Supplier;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface ISupplierService {
	void save(Supplier supplier);

	void delete(long id);

	void update(Supplier supplier);

	Supplier get(long id);

	List<Supplier> listAll();

	PageResult query(QueryObject qo);

}
