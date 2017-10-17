package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/24.
 */
@Setter@Getter
public class OrderChartQueryObject extends  QueryObject {

    private String keyword;
    private Date beginDate;
    private Date endDate;
    private Long supplierId=-1L;
    private Long brandId=-1L;
//    private int status=-1;
    //分组类型
    private String groupType="inputUser.name";

    public Date getBeginDate() {
        if(beginDate==null){
            return null;
        }
        return DateUtil.getBeginDate(beginDate);
    }

    public Date getEndDate() {
        if(endDate==null){
            return null;
        }
        return DateUtil.getEndDate(endDate);
    }

}
