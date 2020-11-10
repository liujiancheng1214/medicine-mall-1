package cn.jdcloud.medicine.mall.api.biz.coupon.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class CouponRecordResultVo {

	@ApiModelProperty(value="")
    private Integer id;
	

	@ApiModelProperty(value="")
    private Integer couponId;
   @ApiModelProperty(value="")
    private String title; //优惠券标题
   @ApiModelProperty(value="")
    private Byte type; //1:代金券 2.折扣券 3.满减券
   @ApiModelProperty(value="")
    private BigDecimal discountAmount; //优惠金额
   @ApiModelProperty(value="")
    private BigDecimal discountLimitAmount; //满减优惠金额
   @ApiModelProperty(value="")
    private String description; //优惠券描述
    private Integer expireDays; //优惠券有效时间单位(天)
    // 有效开始日期
    private String effectiveStratTime;
    // 有效结束时间
    private String effectiveEndTime;

    private Byte couponStatus; //券状态(1为可用;2:为已过期;3:已核销)
	
}
