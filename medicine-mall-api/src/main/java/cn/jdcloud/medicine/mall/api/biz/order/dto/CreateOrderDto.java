package cn.jdcloud.medicine.mall.api.biz.order.dto;

import lombok.Data;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 购买商品,生成订单参数
 */
@Data
@ApiModel("生成订单参数对象")
public class CreateOrderDto {
    //0：普通订单  1：拼团订单   默认 0
    @ApiModelProperty(value = "订单类型  0 普通订单  1 拼团订单  2 预售订单")
    private byte orderType = 0;
    @ApiModelProperty(value = "活动id")
    private Integer promotionId;

    @ApiModelProperty(value = "针对团购商品的时候选的份数")
    private Integer num;
    //用户（买家、药店） 备注
    private String remark;
    //申请退款  创建退款单的时候 需填写原因
    private Byte reason;
    //申请退款 填写 要退款的 订单id
    private String orderId;
    //申请退款  1 后台 申请  2  客户申请
    private Byte adminOrUser;
    //如果创建退款单。这里填写退款类型  1 整单退款 2 部分退款
    private Byte refundType;

    private Integer payType;

    private List<CreateOrderItemDto> list;
    @ApiModelProperty(value = "地址编码")
    private Integer addreddId;
    @ApiModelProperty(value = "用户优惠券编码")
    private Integer userCouponId;
}
