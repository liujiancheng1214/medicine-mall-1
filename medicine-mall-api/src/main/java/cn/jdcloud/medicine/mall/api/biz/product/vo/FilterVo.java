package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.util.List;

import lombok.Data;

@Data
public class FilterVo {
	private List<FilterCategoryVo> categoryList;
	private List<FilterBrandVo> brandList;
}
