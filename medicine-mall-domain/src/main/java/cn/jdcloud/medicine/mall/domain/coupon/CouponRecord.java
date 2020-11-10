package cn.jdcloud.medicine.mall.domain.coupon;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author chenQF
 * @desc 用户领取优惠券记录
 * @date 2020/8/20 0020 18:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_coupon")
public class CouponRecord {
    private Integer id;

    private Integer userId;

    private Integer couponId;

    private Byte couponType; //券类型

    private Date createTime;

    private Date updateTime;

    private Date expireTime; //过期时间

    private Date usedAt; //使用时间

    private Byte couponStatus; //券状态(1为可用;2:为已过期;3:已核销)
}
