package com._520it.wms.service.impl;

import com._520it.wms.domain.Supplier;
import com._520it.wms.mapper.SupplierMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public void save(Supplier supplier) {
        supplierMapper.insert(supplier);
    }

    @Override
    public void delete(long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Supplier supplier) {
        supplierMapper.updateByPrimaryKey(supplier);
    }

    @Override
    public Supplier get(long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> listAll() {
        return supplierMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = supplierMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Supplier> listData = supplierMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
