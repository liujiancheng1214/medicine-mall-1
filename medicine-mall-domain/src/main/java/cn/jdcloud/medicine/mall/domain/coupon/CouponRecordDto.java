package cn.jdcloud.medicine.mall.domain.coupon;

import lombok.Data;

import java.util.List;

/**
 * @author chenQF
 * @desc 优惠券记录添加条件
 * @date 2020/8/20 0020 18:55
 */
@Data
public class CouponRecordDto {

    private List<Integer> userIds; //用户ID集合

    private String couponId; //优惠券ID

    private String sendType; //发放类型 0发给部分人 1发给所有人

}
