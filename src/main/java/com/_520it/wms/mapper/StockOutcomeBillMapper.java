package com._520it.wms.mapper;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.domain.StockOutcomeBillItem;
import com._520it.wms.query.StockOutComeBillQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockOutcomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill stockOutcomeBill);

    StockOutcomeBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOutcomeBill record);

    int queryForCount(StockOutComeBillQueryObject qo);

    List<StockOutcomeBill> queryForList(StockOutComeBillQueryObject qo);

    void insertItem(StockOutcomeBillItem item);

    void deleteItemByBillId(Long stockOutcomeBillId);

    void audit(StockOutcomeBill stockOutcomeBill);
}