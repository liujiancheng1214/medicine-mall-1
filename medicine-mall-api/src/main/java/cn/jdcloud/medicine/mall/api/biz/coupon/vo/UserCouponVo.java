package cn.jdcloud.medicine.mall.api.biz.coupon.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCouponVo {
	@ApiModelProperty("可用状态优惠券列表")
	private List<CouponVo> kyList;
	@ApiModelProperty("过期状态优惠券列表")
	private List<CouponVo> gqList;
//	@ApiModelProperty("核销状态优惠券列表")
//	private List<CouponVo> hxList;
}
