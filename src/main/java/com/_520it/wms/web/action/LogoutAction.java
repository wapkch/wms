package com._520it.wms.web.action;

import com.opensymphony.xwork2.ActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class LogoutAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		// 注销用户,清空session中的数据
		ActionContext.getContext().getSession().clear();
		return LOGIN;
	}
}
