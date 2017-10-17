package com._520it.wms.service;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.StockOutComeBillQueryObject;

public interface IStockOutcomeBillService {
	void save(StockOutcomeBill stockOutcomeBill);

	void delete(long id);

	void update(StockOutcomeBill stockOutcomeBill);

	StockOutcomeBill get(long id);

	PageResult query(StockOutComeBillQueryObject qo);

    void audit(Long id);
}
