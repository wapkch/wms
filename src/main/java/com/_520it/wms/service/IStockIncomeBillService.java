package com._520it.wms.service;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

public interface IStockIncomeBillService {
	void save(StockIncomeBill stockIncomeBill);

	void delete(long id);

	void update(StockIncomeBill stockIncomeBill);

	StockIncomeBill get(long id);

	PageResult query(QueryObject qo);

    void audit(Long id);
}
