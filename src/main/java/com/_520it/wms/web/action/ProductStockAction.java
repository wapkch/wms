package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.ProductStockQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IProductStockService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class ProductStockAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IProductStockService productStockService;
	@Autowired
	private IDepotService depotService;
	@Autowired
	private IBrandService brandService;
	@Getter
	private ProductStockQueryObject qo=new ProductStockQueryObject();

	@RequiredPermission("即时库存报表")
	public String execute() throws Exception {
		putContext("depots",depotService.listAll());
		putContext("brands",brandService.listAll());
		putContext("result", productStockService.query(qo));
		return LIST;
	}
}
