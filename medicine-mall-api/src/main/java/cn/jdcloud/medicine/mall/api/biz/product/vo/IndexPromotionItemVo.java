package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexPromotionItemVo {
	@ApiModelProperty(value = "活动编码")
	private Integer promotionId;
	@ApiModelProperty(value = "商品编码")
	private String itemNo;
	@ApiModelProperty(value = "产品封面")
	private String imgCover;
	@ApiModelProperty(value = "商品名字")
	private String itemName;
	@ApiModelProperty(value = "有效日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date effectiveDate;
	@ApiModelProperty("还剩多少时间结束")
	private long surplusTime;
	@ApiModelProperty(value = "团购价格")
	private BigDecimal promotionPrice;
	@ApiModelProperty(value = "多少起拼")
	private Integer limitNum;
	@ApiModelProperty(value = "已拼数量")
	private Integer soldNum;

}
