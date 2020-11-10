package cn.jdcloud.medicine.mall.api.biz.order.vo;

import java.math.BigDecimal;
import java.util.List;

import cn.jdcloud.medicine.mall.api.biz.coupon.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PageItemVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class OrderPayPageVO {
	@ApiModelProperty("进入类型 1 点击商品直接进入 2 购物车选择进入  3 未支付订单进入")
	private String type;
	@ApiModelProperty("默认地址信息")
	private UserAddressVo  userDefaultAddressVo;
	@ApiModelProperty("购物项信息，针对从购物车进入支付页")
	private List<PageItemVo> pageItemVoList;
	@ApiModelProperty("是否有可用优惠券标识")
	private int couponTag;
	
	@ApiModelProperty("针对未支付订单进来的时候已选择的优惠券")
	private CouponVo couponVo;
	
	
	@ApiModelProperty("是否默认地址标识")
	private int defaultAddressTag;
	
	private Integer addressId;
	
	@ApiModelProperty("商品总价")
	private BigDecimal totalItemFee;
	@ApiModelProperty("运费")
	private BigDecimal transportFee;
	@ApiModelProperty("合计价格")
	private BigDecimal totalFee;
	@ApiModelProperty("总商品数")
	private Integer totalNum;
}
