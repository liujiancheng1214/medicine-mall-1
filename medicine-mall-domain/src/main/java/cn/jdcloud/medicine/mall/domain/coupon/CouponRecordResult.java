package cn.jdcloud.medicine.mall.domain.coupon;

import lombok.Data;

/**
 * @author chenQF
 * @desc 用户领取优惠券记录查询结果
 * @date 2020/8/20 0020 18:07
 */
@Data
public class CouponRecordResult extends CouponRecord{

    private String title; //优惠券标题

    private String userName; //客户姓名

}
