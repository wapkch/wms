package com._520it.wms.mapper;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.query.OrderBillQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderBill record);

    int queryForCount(OrderBillQueryObject qo);

    List<OrderBill> queryForList(OrderBillQueryObject qo);

    void insertItem(OrderBillItem item);

    void deleteItemByBillId(Long orderBillId);

    void audit(OrderBill orderBill);
}