package cn.jdcloud.medicine.mall.api.biz.coupon.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("优惠券对象")
@Data
public class CouponVo {
	@ApiModelProperty(value = "领取记录编码")
	private Integer id;
	@ApiModelProperty(value = "优惠券编码")
	private Integer couponId;
	@ApiModelProperty(value = "优惠券标题")
	private String title; // 优惠券标题
	@ApiModelProperty(value = "1:代金券 2.折扣券 3.满减券")
	private Byte type; // 1:代金券 2.折扣券 3.满减券
	private String typeDesc;
	@ApiModelProperty(value = "优惠金额")
	private BigDecimal discountAmount; // 优惠金额
	@ApiModelProperty(value = "满减优惠金额")
	private BigDecimal discountLimitAmount; // 满减优惠金额
	@ApiModelProperty(value = "优惠券描述")
	private String description; // 优惠券描述
	@ApiModelProperty(value = "优惠券有效时间单位(天)")
	private Integer expireDays; 
	@ApiModelProperty(value = "限制类型: 0 不受限制   1 商品限制  2 品牌限制 3 类别限制 ")
	private Byte limitType; //限制类型: 0 不受限制   1 商品限制  2 品牌限制 3 类别限制 
	    
    private String limitItenIds; //限制商品
	    
	private Integer limitItemNum; //只针对limitType=1 限制商品数量 默认1 当为2时,表示最少购买2件该商品才可使用
	
	// 有效开始日期
	@ApiModelProperty(value = "有效开始日期")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date effectiveStratTime;
	// 有效结束时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@ApiModelProperty(value = "有效结束时间")
    private Date effectiveEndTime; //过期时间
    @ApiModelProperty(value = "优惠券状态 1为可用;2:为已过期;3:已核销")
    private Byte couponStatus; //券状态(1为可用;2:为已过期;3:已核销)

}
