package cn.jdcloud.medicine.mall.api.biz.coupon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.product.vo.CouponVo;
import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponResult;

/**
 * @author chenQF
 * @desc 优惠券接口
 * @date 2020/8/20 0020 17:43
 */
public interface CouponService extends IService<Coupon> {
	
	/**
	 * 查询用户可领取的优惠券列表
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	Page<CouponVo> listCoupon(int pageNum,int pageSize,Integer userId,Byte limitType);
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
