package cn.jdcloud.medicine.mall.api.biz.coupon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("可以优惠券查询对象")
public class ItemNumVo {
	@ApiModelProperty(value="商品主键ID",required=true)
	private Integer itemId;
	@ApiModelProperty(value="商品号",required=true)
	private String itemNo;
	@ApiModelProperty(value="数量",required=true)
	private Integer num;
	@ApiModelProperty(value="SKU",required=true)
	private String sku;
}
