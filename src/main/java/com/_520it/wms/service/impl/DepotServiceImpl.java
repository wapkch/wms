package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.mapper.DepotMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService {

    @Autowired
    private DepotMapper depotMapper;

    @Override
    public void save(Depot depot) {
        depotMapper.insert(depot);
    }

    @Override
    public void delete(long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Depot depot) {
        depotMapper.updateByPrimaryKey(depot);
    }

    @Override
    public Depot get(long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Depot> listAll() {
        return depotMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = depotMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Depot> listData = depotMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
