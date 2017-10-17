package com._520it.wms.web.action;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.service.IOrderBillService;
import com._520it.wms.service.ISupplierService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class OrderBillAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IOrderBillService orderBillService;
	@Autowired
	private ISupplierService supplierService;
	@Getter
	private OrderBill orderBill = new OrderBill();
	@Getter
	private OrderBillQueryObject qo=new OrderBillQueryObject();

	// 查询
	@RequiredPermission("采购订单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("suppliers",supplierService.listAll());
		putContext("result", orderBillService.query(qo));
		return LIST;
	}

	// 删除
	@RequiredPermission("采购订单删除")
	public String delete() {
		try {
			if (orderBill.getId() != null) {
                orderBillService.delete(orderBill.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}
	// 删除
	@RequiredPermission("采购订单审核")
	public String audit() {
		try {
			if (orderBill.getId() != null) {
                orderBillService.audit(orderBill.getId());
				putMsg("审核成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("审核失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("采购订单编辑")
	public String input() throws Exception {
		putContext("suppliers",supplierService.listAll());
		if (orderBill.getId() != null) {
			orderBill = orderBillService.get(orderBill.getId());
			System.out.println("==================");
			System.out.println(orderBill.getItems().size());
		}
		return INPUT;
	}
	// 页面跳转
	@RequiredPermission("采购订单查看")
	public String show() throws Exception {
		if (orderBill.getId() != null) {
			orderBill = orderBillService.get(orderBill.getId());
		}
		return "show";
	}

	// 保存或者更新
	@RequiredPermission("采购订单保存或更新")
	public String saveOrUpdate() {
		try {
			if (orderBill.getId() != null) {
                orderBillService.update(orderBill);
                addActionMessage("更新成功");
            } else {
                orderBillService.save(orderBill);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

}
