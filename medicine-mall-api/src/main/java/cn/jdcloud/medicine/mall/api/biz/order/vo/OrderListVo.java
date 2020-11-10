package cn.jdcloud.medicine.mall.api.biz.order.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class OrderListVo {

	@ApiModelProperty("订单号")
	private String orderId;
	@ApiModelProperty("状态")
	private Byte orderStatus;
	@ApiModelProperty("订单类型 0 普通订单 1 拼团订单")
	private Byte orderType;
	@ApiModelProperty("状态描述")
	private String orderStatusDesc;
	@ApiModelProperty("创建时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	@ApiModelProperty("发货时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 private Date deliveryTime; //发货时间
	@ApiModelProperty("支付时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 private Date payTime;  
	@ApiModelProperty("剩余支付时间")
	private String surplusPayTime;
	@ApiModelProperty("剩余收货时间")
	private String surplusReceivingTime;
	 /** 商品总数*/
	 @ApiModelProperty("商品总数")
     private Integer totalNum;
	 @ApiModelProperty("配送方式描述")
	 private String deliveryMethodDesc;
	 @ApiModelProperty("配送方式")
	 private Byte deliveryMethod;
	 @ApiModelProperty("运费")
	 private BigDecimal transportFee;
    /** 商品总金额*/
	@ApiModelProperty("商品总金额")
    private BigDecimal totalAmount;
    /** 优惠金额*/
	@ApiModelProperty("优惠金额")
    private BigDecimal discountAmount;
    /** 应付金额*/
	@ApiModelProperty("实际支付金额")
    private BigDecimal paymentAmount;
    /**商品项 */
	@ApiModelProperty("商品项")
    private List<OrderItemListVo> itemList;
	@ApiModelProperty("订单地址信息")
	private OrderAddressVo orderAddressVo;
}
