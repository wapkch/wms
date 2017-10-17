package com._520it.wms.util;

import java.lang.reflect.Method;

public class PermissionUtil {

	private PermissionUtil() {
	}

	// 拼接一个权限表达式
	public static String buildExpression(Method method) {
		// 获取到Action的全限定名
		String actionName = method.getDeclaringClass().getName();
		String methodName = method.getName();
		String expression = actionName + ":" + methodName;
		return expression;
	}
}
