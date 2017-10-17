package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.Supplier;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ISupplierService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class SupplierAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ISupplierService supplierService;
	@Getter
	private Supplier supplier = new Supplier();
	@Getter
	private QueryObject qo=new QueryObject();

	// 查询
	@RequiredPermission("供应商列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("result", supplierService.query(qo));
		return LIST;
	}

	// 删除
	@RequiredPermission("供应商删除")
	public String delete() {
		try {
			if (supplier.getId() != null) {
                supplierService.delete(supplier.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("供应商编辑")
	public String input() throws Exception {
		if (supplier.getId() != null) {
			supplier = supplierService.get(supplier.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("供应商保存或更新")
	public String saveOrUpdate() {
		try {
			if (supplier.getId() != null) {
                supplierService.update(supplier);
                addActionMessage("更新成功");
            } else {
                supplierService.save(supplier);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
}
