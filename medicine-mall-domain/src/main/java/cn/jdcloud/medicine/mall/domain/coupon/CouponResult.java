package cn.jdcloud.medicine.mall.domain.coupon;

import lombok.Data;

/**
 * @author chenQF
 * @desc 优惠券类别查询结果
 * @date 2020/8/20 0020 18:07
 */
@Data
public class CouponResult extends Coupon{

    private String creatorName; //创建人

    private String updatorName; //更新人

}
