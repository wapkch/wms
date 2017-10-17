package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.StockOutComeBillQueryObject;
import com._520it.wms.service.IClientService;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IStockOutcomeBillService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class StockOutcomeBillAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IStockOutcomeBillService stockOutcomeBillService;
	@Autowired
	private IDepotService depotService;
	@Autowired
	private IClientService clientService;
	@Getter
	private StockOutcomeBill stockOutcomeBill = new StockOutcomeBill();
	@Getter
	private StockOutComeBillQueryObject qo=new StockOutComeBillQueryObject();

	// 查询
	@RequiredPermission("销售出库单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("depots",depotService.listAll());
		putContext("clients",clientService.listAll());
		putContext("result", stockOutcomeBillService.query(qo));
		return LIST;
	}

	// 删除
	@RequiredPermission("销售出库单删除")
	public String delete() {
		try {
			if (stockOutcomeBill.getId() != null) {
                stockOutcomeBillService.delete(stockOutcomeBill.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("销售出库单编辑")
	public String input() throws Exception {
		putContext("depots",depotService.listAll());
		putContext("clients",clientService.listAll());
		if (stockOutcomeBill.getId() != null) {
			stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
		}
		return INPUT;
	}
	// 页面跳转
	@RequiredPermission("销售出库单查看")
	public String show() throws Exception {
		if (stockOutcomeBill.getId() != null) {
			stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
		}
		return "show";
	}

	// 保存或者更新
	@RequiredPermission("销售出库单保存或更新")
	public String saveOrUpdate() {
		try {
			if (stockOutcomeBill.getId() != null) {
                stockOutcomeBillService.update(stockOutcomeBill);
                addActionMessage("更新成功");
            } else {
                stockOutcomeBillService.save(stockOutcomeBill);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("销售出库单审核")
	public String audit(){
		try {
			if (stockOutcomeBill.getId() != null) {
                stockOutcomeBillService.audit(stockOutcomeBill.getId());
				putMsg("审核成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("审核失败:"+e.getMessage());
		}
		return NONE;
	}
}
