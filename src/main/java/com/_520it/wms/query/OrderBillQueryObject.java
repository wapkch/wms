package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Setter;

import java.util.Date;

@Setter
public class OrderBillQueryObject extends  QueryObject {
    private Date beginDate;
    private Date endDate;
    private Long supplierId=-1L;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public int getStatus() {
        return status;
    }
}
