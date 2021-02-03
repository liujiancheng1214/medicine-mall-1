package cn.jdcloud.medicine.mall.api.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品查询参数")
public class ProductQueryParam {
	private Integer pageNum;
	private Integer pageSize;
	@ApiModelProperty(value="商品分类ID")
	private Integer itemCategoryId;
	@ApiModelProperty(value="商品品牌ID")
	private Integer itemBrandId;
	@ApiModelProperty(value="排序方式  1：价格升序 2 价格降序  3 有效期升序 4 有效期降序")
	private Integer sortType;
	@ApiModelProperty(value="关键字搜索（商品名称）")
	private String searchValue;
}
