package com._520it.wms.service.impl;

import com._520it.wms.domain.Client;
import com._520it.wms.mapper.ClientMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public void save(Client client) {
        clientMapper.insert(client);
    }

    @Override
    public void delete(long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Client client) {
        clientMapper.updateByPrimaryKey(client);
    }

    @Override
    public Client get(long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Client> listAll() {
        return clientMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = clientMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<Client> listData = clientMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }
}
