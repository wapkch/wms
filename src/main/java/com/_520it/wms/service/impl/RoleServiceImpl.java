package com._520it.wms.service.impl;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.Role;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.RoleMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void save(Role role) {
        roleMapper.save(role);
        // 1. 保存角色和权限的关系
        List<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions) {
            roleMapper.saveRelation(role.getId(),permission.getId());
        }
        // 2. 保存角色和菜单的关系
        List<SystemMenu> menus = role.getMenus();
        for (SystemMenu menu : menus) {
            roleMapper.saveMenuRelations(role.getId(),menu.getId());
        }
    }

    @Override
    public void delete(long id) {
        // 先删除角色权限关系
        roleMapper.deletePermissionRelation(id);
        roleMapper.delete(id);
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);
        // 1. 保存角色和权限的关系
        roleMapper.deletePermissionRelation(role.getId());
        List<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions) {
            roleMapper.saveRelation(role.getId(),permission.getId());
        }
        // 2. 保存角色和菜单的关系
        roleMapper.deleteMenuRelations(role.getId());
        List<SystemMenu> menus = role.getMenus();
        for (SystemMenu menu : menus) {
            roleMapper.saveMenuRelations(role.getId(),menu.getId());
        }
    }

    @Override
    public Role get(long id) {
        return roleMapper.get(id);
    }

    @Override
    public List<Role> listAll() {
        return roleMapper.listAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = roleMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(null, 0, 1, qo.getPageSize());
        }
        List<Role> listData = roleMapper.queryForList(qo);
        return new PageResult(listData, count, qo.getCurrentPage(), qo.getPageSize());
    }
}
