package cn.jdcloud.medicine.mall.api.biz.coupon.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponService;
import cn.jdcloud.medicine.mall.dao.coupon.CouponMapper;
import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenQF
 * @desc 优惠券业务层
 * @date 2020/8/20 0020 17:43
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>  implements CouponService {

    @Resource
    CouponMapper couponMapper;

    @Override
    public Page<CouponResult> listCoupon(Page<CouponResult> page, String type) {
        page.setRecords(couponMapper.listCoupon(page, type));
        page.setTotal(couponMapper.listCount(type));
        return page;
    }

    @Override
    public void delete(Integer id) {
        int count = couponMapper.deleteById(id);
        if(count <= 0){
            throw new ApiException(1000, "优惠券删除失败");
        }
    }
}
