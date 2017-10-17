package com._520it.wms.web.action;

import com._520it.wms.domain.Employee;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.util.UserContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Setter
	private String username;
	@Setter
	private String password;
	@Autowired
	private IEmployeeService employeeService;

	@Override
	public String execute() throws Exception {
		Employee e = employeeService.checkLogin(username, password);
		if (e != null) {
			// 将用户信息添加到session中
			UserContext.setCurrentUser(e);
			// 跳转到mainAction中
			return "main";
		} else {
			// 将错误信息添加到值栈中
			addActionError("亲,用户名或密码错误");
			// 跳转到login.jsp
			return LOGIN;
		}
	}
}
