package com._520it.wms.web.action;

import com._520it.wms.domain.Product;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IProductService;
import com._520it.wms.util.FileUploadUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
@Scope("prototype")
public class ProductAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IProductService productService;
	@Autowired
	private IBrandService brandService;
	@Setter
	private File pic;
	@Setter
	private String picFileName;
	@Getter
	private Product product = new Product();
	@Getter
	private ProductQueryObject qo=new ProductQueryObject();

	// 查询
	@RequiredPermission("商品列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		putContext("brands",brandService.listAll());
		PageResult pageResult = productService.query(qo);
		putContext("result", productService.query(qo));
		return LIST;
	}
	// 查询
	@RequiredPermission("商品选择")
	@InputConfig(methodName = "input")
	public String selectProduct() throws Exception {
		qo.setPageSize(20);
		putContext("brands",brandService.listAll());
		putContext("result", productService.query(qo));
		return "selectProduct";
	}

	// 删除
	@RequiredPermission("商品删除")
	public String delete() {
		try {
			if (product.getId() != null) {
				if(StringUtils.isNotEmpty(product.getImagePath())){
					FileUploadUtil.deleteFile(product.getImagePath());
				}
                productService.delete(product.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("商品编辑")
	public String input() throws Exception {
		putContext("brands",brandService.listAll());
		if (product.getId() != null) {
			product = productService.get(product.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("商品保存或更新")
	public String saveOrUpdate() {
		try {
			if(pic!=null){
				if(StringUtils.isNotEmpty(product.getImagePath())){
					FileUploadUtil.deleteFile(product.getImagePath());
				}
				String path = FileUploadUtil.uploadFile(pic, picFileName);
				product.setImagePath(path);
			}
			if (product.getId() != null) {
                productService.update(product);
                addActionMessage("更新成功");
            } else {
                productService.save(product);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
}
