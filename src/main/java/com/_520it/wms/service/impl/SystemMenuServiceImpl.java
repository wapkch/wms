package com._520it.wms.service.impl;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.SystemMenuMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public void save(SystemMenu systemMenu) {
        systemMenuMapper.insert(systemMenu);
    }

    @Override
    public void delete(long id) {
        systemMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemMenu systemMenu) {
        systemMenuMapper.updateByPrimaryKey(systemMenu);
    }

    @Override
    public SystemMenu get(long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemMenu> listAll() {
        return systemMenuMapper.selectAll();
    }

    @Override
    public PageResult query(SystemMenuQueryObject qo) {
        int count = systemMenuMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(null,0,1,qo.getPageSize());
        }
        List<SystemMenu> listData = systemMenuMapper.queryForList(qo);
        return new PageResult(listData,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public List<Map<String, Object>> selectMenus(Long parentId) {
        SystemMenu systemMenu = systemMenuMapper.selectByPrimaryKey(parentId);
        List<Map<String, Object>> menus = new ArrayList<>();
        while (systemMenu!=null){
            Map<String, Object> map = new HashMap<>();
            map.put("menuId",systemMenu.getId());
            map.put("menuName",systemMenu.getName());
            menus.add(map);
            if (systemMenu.getParent()!=null){
                systemMenu = systemMenuMapper.selectByPrimaryKey(systemMenu.getParent().getId());
            }else{
                break;
            }
        }
        Collections.reverse(menus);
        return menus;
    }

    @Override
    public List<SystemMenu> listSonMenus() {
        return systemMenuMapper.listSonMenus();
    }

    @Override
    public List<Map<String, Object>> queryMenusBySn(String parentSn) {
        return systemMenuMapper.queryMenusBySn(parentSn);
    }
}
