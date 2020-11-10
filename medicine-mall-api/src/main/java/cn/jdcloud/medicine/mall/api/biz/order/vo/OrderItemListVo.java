package cn.jdcloud.medicine.mall.api.biz.order.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderItemListVo {
	@ApiModelProperty("订单号")
	private String orderId;
	@ApiModelProperty("用户编码")
	private Integer userId;
	@ApiModelProperty("商品编码")
	private Integer itemId;
	@ApiModelProperty("商品号")
	private String itemNo;
	@ApiModelProperty("商品名")
	private String itemName;
	private String factory;
	@ApiModelProperty("有效期")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date effectiveDate;
	@ApiModelProperty("品牌编码")
	private Integer brandId;
	@ApiModelProperty("品牌")
	private String brandName;
    @ApiModelProperty(value="零售价格")
    private BigDecimal retailPrice;
	@ApiModelProperty(value="平台价格")
	private BigDecimal platformPrice;
	@ApiModelProperty("数量")
	private Integer itemNum;
	@ApiModelProperty("商品封面")
	private String imgCover;
	@ApiModelProperty("订单状态")
	private Byte orderStatus;
	private String sku;
}
