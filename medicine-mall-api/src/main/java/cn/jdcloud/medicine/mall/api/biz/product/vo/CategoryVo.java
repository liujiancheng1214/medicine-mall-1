package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.util.List;

import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CategoryVo {
	@ApiModelProperty(value="父级")
	 private Integer parentId;
	@ApiModelProperty(value="值")
	 private Integer id;
	@ApiModelProperty(value="名字")
	  private String categoryName;
	@ApiModelProperty(value="图片地址")
	 private String categoryImg;
	@ApiModelProperty(value="子类集")
    private List<CategoryVo> children;
	
	public CategoryVo() {}
	public CategoryVo(ItemCategory  itemCategory) {
		this.parentId=itemCategory.getParentId();
		this.id=itemCategory.getId();
		this.categoryName=itemCategory.getCategoryName();
		this.categoryImg=itemCategory.getCategoryImg();
	}
	
}
