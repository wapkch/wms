package com._520it.wms.mapper;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.SaleChartQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/24.
 */
@Repository
public interface ChartMapper {

    //订货报表
    List<Map<String,Object>> orderChart(OrderChartQueryObject qo);

    //销售报表
    List<Map<String,Object>> saleChart(SaleChartQueryObject qo);
}
