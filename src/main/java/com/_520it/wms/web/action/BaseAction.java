package com._520it.wms.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 8734838916513953542L;

	public static final String LIST = "list";

	// 向context区域添加共享数据
	public void putContext(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	public void putMsg(Object obj) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
