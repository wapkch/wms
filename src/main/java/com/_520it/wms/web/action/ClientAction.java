package com._520it.wms.web.action;

import com._520it.wms.domain.Client;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IClientService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class ClientAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IClientService clientService;
	@Getter
	private Client client = new Client();
	@Getter
	private QueryObject qo=new QueryObject();

	// 查询
	@RequiredPermission("客户列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("result", clientService.query(qo));
		return LIST;
	}

	// 删除
	@RequiredPermission("客户删除")
	public String delete() {
		try {
			if (client.getId() != null) {
                clientService.delete(client.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("客户编辑")
	public String input() throws Exception {
		if (client.getId() != null) {
			client = clientService.get(client.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("客户保存或更新")
	public String saveOrUpdate() {
		try {
			if (client.getId() != null) {
                clientService.update(client);
                addActionMessage("更新成功");
            } else {
                clientService.save(client);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
}
