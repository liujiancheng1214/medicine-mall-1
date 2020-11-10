package cn.jdcloud.medicine.mall.domain.coupon;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_order_coupon")
public class OrderCoupon {
    private Integer id;

    private String orderId;

    private Integer userCouponId;

    private BigDecimal discoutAmount;

    private Date createTime;

    private Date updateTime;

}