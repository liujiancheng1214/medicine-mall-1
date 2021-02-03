package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexCouponItemVo {
	
	@ApiModelProperty(value = "商品编码")
	private String itemNo;
	@ApiModelProperty(value = "产品封面")
	private String imgCover;
	@ApiModelProperty(value = "商品名字")
	private String itemName;
	 @ApiModelProperty(value="平台价格")
	 private BigDecimal platformPrice;
	 @ApiModelProperty(value="折扣价格")
	 private BigDecimal discountPrice;
	 

}
