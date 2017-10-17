package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemMenuQueryObject extends QueryObject {
    //父级菜单的id
    private Long parentId=-1L;

    private String parentSn;

}
