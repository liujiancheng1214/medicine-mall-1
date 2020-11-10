package cn.jdcloud.medicine.mall.domain.coupon;

import lombok.Data;

/**
 * @author chenQF
 * @desc 优惠券记录查询条件
 * @date 2020/8/20 0020 18:55
 */
@Data
public class CouponDto {

    private String userId; //用户ID

    private String couponId; //优惠券ID

    private String startTime; //领取时间开始范围

    private String endTime; //领取时间结束范围

    private String couponType; //券类型

    private String couponStatus; //券状态(1为可用;2:为已过期;3:已核销)

}
