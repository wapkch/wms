package com._520it.wms.service.impl;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.mapper.PermissionMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.web.action.BaseAction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService, ApplicationContextAware {

    @Autowired
    private PermissionMapper permissionMapper;

    private ApplicationContext ctx;

    @Override
    public void save(Permission permission) {
        permissionMapper.save(permission);
    }

    @Override
    public void delete(long id) {
        permissionMapper.delete(id);
    }

    @Override
    public List<Permission> listAll() {
        return permissionMapper.listAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = permissionMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(null, 0, 1, qo.getPageSize());
        }
        List<Permission> listData = permissionMapper.queryForList(qo);
        return new PageResult(listData, count, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void reload() {
        Set<String> expressions = new HashSet<>();
        List<Permission> permissions = permissionMapper.listAll();
        for (Permission permission : permissions) {
            expressions.add(permission.getExpression());
        }

        // 1. 从spring容器中获取所有的actions
        Collection<BaseAction> actions = ctx.getBeansOfType(BaseAction.class).values();
        // 2. 获取到action中贴有注解的方法
        for (BaseAction action : actions) {
            Method[] methods = action.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequiredPermission.class)) {
                    // 3. 拼接权限表达式
                    String actionName = method.getDeclaringClass().getName();
                    String methodName = method.getName();
                    String expression = actionName + ":" + methodName;
                    String permissionName = method.getAnnotation(RequiredPermission.class).value();
                    // 4. 保存权限
                    if (!expressions.contains(expression)) {
                        Permission permission = new Permission();
                        permission.setName(permissionName);
                        permission.setExpression(expression);
                        permissionMapper.save(permission);
                    }
                }
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
