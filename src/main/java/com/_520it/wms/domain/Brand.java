package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

//品牌对象
@Setter@Getter
public class Brand extends  BaseDomain {
    //品牌名称
    private String name;
    //品牌编码
    private String sn;

}