package com._520it.wms.service;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {
	void save(SystemMenu systemMenu);

	void delete(long id);

	void update(SystemMenu systemMenu);

	SystemMenu get(long id);

	List<SystemMenu> listAll();

	PageResult query(SystemMenuQueryObject qo);

	/**
	 * 根据parentId查找当前菜单的上级菜单id和名称
	 * @param parentId 上一级菜单的id
	 * @return 返回map集合
	 */
    List<Map<String, Object>> selectMenus(Long parentId);


	List<SystemMenu> listSonMenus();

    List<Map<String,Object>> queryMenusBySn(String parentSn);
}
