package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;


@Setter@Getter
public class ProductQueryObject extends  QueryObject {
    //编码/名称
    private String keyword;
    //品牌Id
    private Long brandId=-1L;

}
