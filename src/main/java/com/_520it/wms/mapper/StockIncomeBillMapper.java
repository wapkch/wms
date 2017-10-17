package com._520it.wms.mapper;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockIncomeBillItem;
import com._520it.wms.query.QueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill stockIncomeBill);

    StockIncomeBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(StockIncomeBill record);

    int queryForCount(QueryObject qo);

    List<StockIncomeBill> queryForList(QueryObject qo);

    void insertItem(StockIncomeBillItem item);

    void deleteItemByBillId(Long stockIncomeBillId);

    void audit(StockIncomeBill stockIncomeBill);
}