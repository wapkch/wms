package com._520it.wms.service;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.query.PageResult;

public interface IOrderBillService {
	void save(OrderBill orderBill);

	void delete(long id);

	void update(OrderBill orderBill);

	OrderBill get(long id);

	PageResult query(OrderBillQueryObject qo);

    void audit(Long orderBillId);
}
