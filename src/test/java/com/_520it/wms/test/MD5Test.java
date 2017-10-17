package com._520it.wms.test;

import com._520it.wms.util.MD5;

/**
 * Created by Administrator on 2017/9/16.
 */
public class MD5Test {

    public static void main(String[] args) {
        String text="67890";
        String salt="@@wdt"; //调料 盐
        System.out.println(MD5.encode(salt+text+salt));
    }
}
