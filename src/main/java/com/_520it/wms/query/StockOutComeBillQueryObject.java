package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter@Getter
public class StockOutComeBillQueryObject extends QueryObject {
    private Date beginDate;
    private Date endDate;
    private Long depotId=-1L;
    private Long clientId=-1L;
    private int status=-1;

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
