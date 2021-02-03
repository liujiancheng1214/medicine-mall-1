package cn.jdcloud.medicine.mall.api.biz.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponRecordService;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponService;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderListVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.coupon.CouponMapper;
import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponResult;

/**
 * @author chenQF
 * @desc 优惠券业务层
 * @date 2020/8/20 0020 17:43
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>  implements CouponService {

    @Resource
    private  CouponMapper couponMapper;
    @Resource
    private CouponRecordService couponRecordService;

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

	@Override
	public Page<CouponVo> listCoupon(int pageNum, int pageSize, Integer userId,Byte limitType) {
		Page<Coupon> page=new Page<Coupon>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		
		QueryWrapper<Coupon> queryWrapper =new QueryWrapper<Coupon>();
		// 过滤条件  时间过期  用户已经领取
		// 用户已经领取过的优惠券Id
		List<Integer> couponIds=null;
		if(userId!=null) {
			couponIds=couponRecordService.userCouponRecordIds(userId);
		}
		// 优惠券的过期时间 
		queryWrapper.gt("expire_time", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		queryWrapper.notIn(couponIds!=null&&couponIds.size()>0, "id", couponIds);
		queryWrapper.eq(limitType != null, "limit_type", limitType);
		
		queryWrapper.orderByDesc("create_time");
		page=couponMapper.selectPage(page, queryWrapper);
		
		Page<CouponVo> pageVo=new Page<CouponVo>();
		BeanUtil.copyProperties(page, pageVo);
		List<CouponVo>voList=BeanUtil.copyPropsForList(page.getRecords(), CouponVo.class);
		pageVo.setRecords(voList);
		return pageVo;
	}
}
