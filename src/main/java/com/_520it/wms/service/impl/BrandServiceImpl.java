package com._520it.wms.service.impl;

import com._520it.wms.domain.Brand;
import com._520it.wms.mapper.BrandMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void save(Brand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public void delete(long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public Brand get(long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> listAll() {
        return brandMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = brandMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Brand> listData = brandMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
