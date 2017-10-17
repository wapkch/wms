package com._520it.wms.web.action;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class DepotAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IDepotService depotService;
	@Getter
	private Depot depot = new Depot();
	@Getter
	private QueryObject qo=new QueryObject();

	// 查询
	@RequiredPermission("仓库列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("result", depotService.query(qo));
		return LIST;
	}

	// 删除
	@RequiredPermission("仓库删除")
	public String delete() {
		try {
			if (depot.getId() != null) {
                depotService.delete(depot.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("仓库编辑")
	public String input() throws Exception {
		if (depot.getId() != null) {
			depot = depotService.get(depot.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("仓库保存或更新")
	public String saveOrUpdate() {
		try {
			if (depot.getId() != null) {
                depotService.update(depot);
                addActionMessage("更新成功");
            } else {
                depotService.save(depot);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
}
