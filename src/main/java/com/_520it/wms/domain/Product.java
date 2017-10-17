package com._520it.wms.domain;

import com._520it.wms.util.FileUploadUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter@Getter
public class Product extends BaseDomain {
    private String name;
    private String sn;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private String imagePath;
    private String intro;
    private Brand brand;

    public String getSmallImagePath(){
        String smallImagePath = imagePath.substring(0, imagePath.lastIndexOf(".")) + FileUploadUtil.suffix + imagePath.substring(imagePath.lastIndexOf("."));
        return smallImagePath;
    }

    public String getProductJson(){
        Map<String,Object> map = new HashMap<>();
        map.put("pId",getId());
        map.put("name",name);
        map.put("costPrice",costPrice);
        map.put("salePrice",salePrice);
        map.put("brandName",brand==null?"":brand.getName());
        return JSON.toJSONString(map);
    }

}
