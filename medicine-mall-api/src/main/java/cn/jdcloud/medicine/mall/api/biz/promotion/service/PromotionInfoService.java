package cn.jdcloud.medicine.mall.api.biz.promotion.service;

import cn.jdcloud.medicine.mall.api.biz.promotion.dto.PromotionDto;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionVo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PromotionInfoService extends IService<PromotionInfo> {
	
	
	
    /**
     * 促销活动列表分页
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<PromotionVo> page(Page<PromotionInfo> page, QueryWrapper<PromotionInfo> queryWrapper);

    /**
     * 添加活动
     * @param promotionDto
     * @param userId
     * @return
     */
    boolean save(PromotionDto promotionDto,Integer userId);

    /**
     * 自动下架
     * @param promotionId
     * @return
     */
    PromotionInfoResult getOne(Integer promotionId);
}
