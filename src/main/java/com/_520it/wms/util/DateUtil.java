package com._520it.wms.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/22.
 */
public class DateUtil {

    //获取到current日期的开始时间, 即 00:00:00
    public static Date getBeginDate(Date current){
        Calendar instance = Calendar.getInstance();
        instance.setTime(current);
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        return instance.getTime();
    }

    //获取到current日期的结束时间: 即23:59:59
    public static Date getEndDate(Date current){
        Calendar instance = Calendar.getInstance();
        instance.setTime(current);
        instance.set(Calendar.HOUR_OF_DAY,23);
        instance.set(Calendar.MINUTE,59);
        instance.set(Calendar.SECOND,59);
        return instance.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getBeginDate(new Date()));
        System.out.println(getEndDate(new Date()));
    }
}
