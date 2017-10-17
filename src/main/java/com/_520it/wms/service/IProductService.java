package com._520it.wms.service;

import com._520it.wms.domain.Product;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductQueryObject;

public interface IProductService {
	void save(Product product);

	void delete(long id);

	void update(Product product);

	Product get(long id);

	PageResult query(ProductQueryObject qo);

}
