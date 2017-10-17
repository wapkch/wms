package com._520it.wms.web.action;

import com._520it.wms.domain.Brand;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IBrandService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class BrandAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IBrandService brandService;
	@Getter
	private Brand brand = new Brand();
	@Getter
	private QueryObject qo=new QueryObject();

	// 查询
	@RequiredPermission("品牌列表")
	public String execute() throws Exception {
		PageResult result = brandService.query(qo);
		putContext("result",result);
        return LIST;
	}

	// 删除
	@RequiredPermission("品牌删除")
	public String delete() {
		try {
			if (brand.getId() != null) {
                brandService.delete(brand.getId());
				putMsg("删除成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			putMsg("删除失败:"+e.getMessage());

		}
		return NONE;
	}

	// 页面跳转
	@RequiredPermission("品牌编辑")
	public String input() throws Exception {
		if (brand.getId() != null) {
			brand = brandService.get(brand.getId());
		}
		return INPUT;
	}

	// 保存或者更新
	@RequiredPermission("品牌保存或更新")
	public String saveOrUpdate() {
		try {
			if (brand.getId() != null) {
                brandService.update(brand);
                addActionMessage("更新成功");
            } else {
                brandService.save(brand);
                addActionMessage("新增成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
}
