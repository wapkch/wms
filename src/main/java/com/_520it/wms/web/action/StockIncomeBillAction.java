package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IStockIncomeBillService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class StockIncomeBillAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IStockIncomeBillService stockIncomeBillService;
	@Autowired
	private IDepotService depotService;
	@Getter
	private StockIncomeBill stockIncomeBill = new StockIncomeBill();
	@Getter
	private QueryObject qo=new QueryObject();

	// 查询
	@RequiredPermission("采购入库单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("result", stockIncomeBillService.query(qo));
		return LIST;
	}

	// 删除
	@RequiredPermission("采购入库单删除")
	public String delete() {
		try {
			if (stockIncomeBill.getId() != null) {
                stockIncomeBillService.delete(stockIncomeBill.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("采购入库单编辑")
	public String input() throws Exception {
		putContext("depots",depotService.listAll());
		if (stockIncomeBill.getId() != null) {
			stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
		}
		return INPUT;
	}
	// 页面跳转
	@RequiredPermission("采购入库单查看")
	public String show() throws Exception {
		if (stockIncomeBill.getId() != null) {
			stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
		}
		return "show";
	}

	// 保存或者更新
	@RequiredPermission("采购入库单保存或更新")
	public String saveOrUpdate() {
		try {
			if (stockIncomeBill.getId() != null) {
                stockIncomeBillService.update(stockIncomeBill);
                addActionMessage("更新成功");
            } else {
                stockIncomeBillService.save(stockIncomeBill);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("采购入库单审核")
	public String audit(){
		try {
			if (stockIncomeBill.getId() != null) {
                stockIncomeBillService.audit(stockIncomeBill.getId());
				putMsg("审核成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("审核失败:"+e.getMessage());
		}
		return NONE;
	}
}
