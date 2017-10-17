package com._520it.wms.mapper;

import com._520it.wms.domain.SaleAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleAccountMapper {

    int insert(SaleAccount record);

}