package cn.jdcloud.medicine.mall.api.biz.product.vo;

import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("分类树")
public class CategoryTreeVo {
	@ApiModelProperty(value="父级")
    private Integer parentId;
	@ApiModelProperty(value="值")
    private Integer value;
	@ApiModelProperty(value="名字")
    private String label;
	@ApiModelProperty(value="子类集")
    private List<CategoryTreeVo> children;

    public CategoryTreeVo() {}

    public CategoryTreeVo(ItemCategory category) {
        this.parentId = category.getParentId();
        this.value = category.getId();
        this.label = category.getCategoryName();
    }
}
