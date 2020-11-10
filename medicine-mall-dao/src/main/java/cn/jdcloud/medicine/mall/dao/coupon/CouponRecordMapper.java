package cn.jdcloud.medicine.mall.dao.coupon;


import cn.jdcloud.medicine.mall.domain.coupon.CouponDto;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecord;
import cn.jdcloud.medicine.mall.domain.user.UserDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CouponRecordMapper extends BaseMapper<CouponRecord> {

    List<CouponRecord> listCouponRecord(@Param("page")Page<CouponRecord> page, @Param("couponDto")CouponDto couponDto);

    Integer listCount(@Param("couponDto") CouponDto couponDto);

    int insertCouponRecord(@Param("list")List<CouponRecord> list);
}