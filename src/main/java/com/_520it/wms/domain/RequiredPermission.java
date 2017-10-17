package com._520it.wms.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//说明注解能够保存的时期(一般自定义的注解都要保存到运行时期)
@Retention(RetentionPolicy.RUNTIME)
// 说明该注解能够贴的位置
@Target(ElementType.METHOD)
public @interface RequiredPermission {
	String value();
}
