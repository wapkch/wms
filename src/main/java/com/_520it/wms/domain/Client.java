package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

//客户管理
@Setter
@Getter
public class Client extends BaseDomain {
    private String sn;
    private String name;
    private String phone;
}
