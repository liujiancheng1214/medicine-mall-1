package cn.jdcloud.medicine.mall.dao.promotion;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoDto;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoResult;

public interface PromotionInfoMapper extends BaseMapper<PromotionInfo> {

    PromotionInfoResult getOne(Integer promotionId);

    PromotionInfoDto queryPromotionInfo(@Param("promotionId")Integer promotionId);
}
