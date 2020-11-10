package cn.jdcloud.medicine.mall.dao.coupon;


import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CouponMapper extends BaseMapper<Coupon> {

    List<CouponResult> listCoupon(@Param("page") Page<CouponResult> page, @Param("type") String type);

    Integer listCount(@Param("type") String type);

}