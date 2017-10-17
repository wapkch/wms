package com._520it.wms.web.interceptor;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.util.PermissionUtil;
import com._520it.wms.util.UserContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.lang.reflect.Method;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SecurityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 获取到登陆的用户
		Employee currentEmp = UserContext.getCurrentUser();
		// 判断用户是否是超级管理员:是 放行
		if (currentEmp.isAdmin()) {
			return invocation.invoke();
		}
		// 判断用户访问的方法上是否贴了RequiredPermission标签
		// 没有:放行
		// 获取到访问的action对象
		Object action = invocation.getProxy().getAction();
		// 获取到访问的action对象中的方法对象
		String methodName = invocation.getProxy().getMethod();
		Method method = action.getClass().getMethod(methodName);
		if (!method.isAnnotationPresent(RequiredPermission.class)) {
			return invocation.invoke();
		}
		// 执行权限校验
		// 获取到用户拥有的所有的权限表达式
		Set<String> expressions =UserContext.getCurrentUserPermissions();
		// 拼接处用户访问的action中的方法对应的权限表达式
		String expression = PermissionUtil.buildExpression(method);
		// 判断访问的权限表达式是否在拥有的权限表达式集合中
		if (expressions.contains(expression)) {
			// 在:放行
			return invocation.invoke();
		}
		// 没在:跳转到一个提示页面即可
		return "nopermission";
	}

}
