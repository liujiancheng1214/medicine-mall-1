package cn.jdcloud.medicine.mall.api.biz.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchItem {
	@ApiModelProperty("商品编码")
	private String  itemNo;
	@ApiModelProperty("商品名称")
	private  String itemName;
}
