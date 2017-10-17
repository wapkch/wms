package com._520it.wms.web.action;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.SaleChartQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IChartService;
import com._520it.wms.service.IClientService;
import com._520it.wms.service.ISupplierService;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/24.
 */
@Controller
@Scope("prototype")
public class ChartAction extends BaseAction {
    @Autowired
    private IChartService chartService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private IClientService clientService;

    @Getter
    private OrderChartQueryObject oqo=new OrderChartQueryObject();

    @Getter
    private SaleChartQueryObject sqo=new SaleChartQueryObject();


    public String orderChart(){
        List<Map<String, Object>> mapList = chartService.queryOrderChart(oqo);
        putContext("mapList",mapList);
        putContext("suppliers",supplierService.listAll());
        putContext("brands",brandService.listAll());
        return "orderChart";
    }

    public String saleChart(){
        List<Map<String, Object>> mapList = chartService.querySaleChart(sqo);
        putContext("clients",clientService.listAll());
        putContext("brands",brandService.listAll());
        putContext("mapList",mapList);
        return "saleChart";
    }

    public String saleChartByLine(){
        List<Object> groupTypeList=new ArrayList<>();
        List<Object> amountList=new ArrayList<>();
        List<Map<String, Object>> mapList = chartService.querySaleChart(sqo);
        for (Map<String, Object> item : mapList) {
            groupTypeList.add(item.get("groupType"));
            amountList.add(item.get("totalAmount"));
        }
        if("inputUser.name".equals(sqo.getGroupType())){
            putContext("groupType","销售人员");
        }else if("p.name".equals(sqo.getGroupType())){
            putContext("groupType","货品名称");
        }else if("c.name".equals(sqo.getGroupType())){
            putContext("groupType","客户");
        }else if("b.name".equals(sqo.getGroupType())){
            putContext("groupType","货品品牌");
        }else if("date_format(sc.vdate,'%Y-%m')".equals(sqo.getGroupType())){
            putContext("groupType","销售日期(月)");
        }else if("date_format(sc.vdate,'%Y-%m-%d')".equals(sqo.getGroupType())){
            putContext("groupType","销售日期(日)");
        }
        putContext("groupTypeList", JSON.toJSONString(groupTypeList));
        putContext("amountList", JSON.toJSONString(amountList));
        return "saleChartByLine";
    }

    public String saleChartByPie(){
        List<Map<String,Object>>  groupList=new ArrayList<>();
        BigDecimal max=BigDecimal.ZERO;
        List<Map<String, Object>> mapList = chartService.querySaleChart(sqo);
        for (Map<String, Object> item : mapList) {
            Map map=new HashMap();
            map.put("name",item.get("groupType"));
            map.put("value",item.get("totalAmount"));
            if(max.compareTo((BigDecimal)item.get("totalAmount"))<0){
                max=(BigDecimal)item.get("totalAmount");
            }
            groupList.add(map);
        }
        if("inputUser.name".equals(sqo.getGroupType())){
            putContext("groupType","销售人员");
        }else if("p.name".equals(sqo.getGroupType())){
            putContext("groupType","货品名称");
        }else if("c.name".equals(sqo.getGroupType())){
            putContext("groupType","客户");
        }else if("b.name".equals(sqo.getGroupType())){
            putContext("groupType","货品品牌");
        }else if("date_format(sc.vdate,'%Y-%m')".equals(sqo.getGroupType())){
            putContext("groupType","销售日期(月)");
        }else if("date_format(sc.vdate,'%Y-%m-%d')".equals(sqo.getGroupType())){
            putContext("groupType","销售日期(日)");
        }
        putContext("groupList",JSON.toJSONString(groupList));
        putContext("max",max);
        return "saleChartByPie";
    }

}
