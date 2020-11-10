package cn.jdcloud.medicine.mall.api.biz.coupon.service;

import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author chenQF
 * @desc 优惠券接口
 * @date 2020/8/20 0020 17:43
 */
public interface CouponService extends IService<Coupon> {
    /**
     * 查询优惠券类别列表
     * @param page
     * @param type
     * @return
     */
    Page<CouponResult> listCoupon(Page<CouponResult> page, String type);

    /**
     * 删除优惠券类别
     * @param id
     */
    void delete(Integer id);
}
