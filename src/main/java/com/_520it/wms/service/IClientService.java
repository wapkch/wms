package com._520it.wms.service;

import com._520it.wms.domain.Client;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IClientService {
	void save(Client client);

	void delete(long id);

	void update(Client client);

	Client get(long id);

	List<Client> listAll();

	PageResult query(QueryObject qo);

}
