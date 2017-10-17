package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

//供应商管理
@Setter@Getter
public class Supplier extends  BaseDomain {
    //供应商名称
    private  String name;
    //联系电话
    private String phone;
    //地址
    private String address;
}
