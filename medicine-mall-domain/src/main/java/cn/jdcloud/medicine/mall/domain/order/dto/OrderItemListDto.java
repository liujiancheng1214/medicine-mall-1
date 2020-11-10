package cn.jdcloud.medicine.mall.domain.order.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrderItemListDto {
	private String orderId;
	private Integer userId;
	private Integer itemId;
	private String itemNo;
	private String itemName;
	private String effectiveDate;
	private Integer brandId;
	private String brandName;
	private String factory;
	private BigDecimal retailPrice;
	private BigDecimal platformPrice;
	private Integer itemNum;
	private String imgCover;
	private Byte orderStatus;
	private String sku;
}
