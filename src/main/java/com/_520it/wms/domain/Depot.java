package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

//仓库
@Setter@Getter
public class Depot extends BaseDomain {
    //仓库地址
    private String location;
    //仓库名称
    private String name;
}
