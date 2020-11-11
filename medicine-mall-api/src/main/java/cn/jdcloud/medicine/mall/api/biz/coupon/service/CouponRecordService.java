package cn.jdcloud.medicine.mall.api.biz.coupon.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.coupon.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.ItemNumVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.UserCouponVo;
import cn.jdcloud.medicine.mall.domain.coupon.CouponDto;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecord;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecordDto;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecordResult;

/**
 * @author chenQF
 * @desc 优惠券领取记录接口
 * @date 2020/8/20 0020 17:43
 */
public interface CouponRecordService extends IService<CouponRecord> {
	
	/**
	 * 查询可用优惠券数量
	 */
	
	int  queryCouponNum(Integer userId);
    /**
     * 查询优惠券领取记录列表
     * @param page
     * @param couponDto
     * @return
     */
    Page<CouponRecordResult> listCouponRecord(Page<CouponRecord> page, CouponDto couponDto);
    
    
    UserCouponVo listCouponRecordByUserIdAndStatus(Integer userId,String status);
    	
    List<CouponVo> listCouponRecordByUserIdAndItemNos(Integer userId,List<ItemNumVo> list);
    

    /**
     * 新增优惠券记录（可批量）
     * @param dto
     */
    void add(CouponRecordDto dto);

    /**
     * 更新优惠券记录
     * @param record
     */
    void update(CouponRecord record);
}
