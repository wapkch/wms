package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//系统菜单
@Setter@Getter
public class SystemMenu extends BaseDomain {
    //菜单名称
    private String name;
    //url地址
    private String url;
    //菜单编码
    private String sn;
    //上级菜单 多对一关系(parent_id)
    private SystemMenu parent;
    private List<SystemMenu> children=new ArrayList<>();

    public String getParentName(){
        if(parent==null){
            return "根目录";
        }
        return parent.getName();
    }
}
