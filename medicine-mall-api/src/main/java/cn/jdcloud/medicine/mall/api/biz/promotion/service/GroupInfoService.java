package cn.jdcloud.medicine.mall.api.biz.promotion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionInfoVo;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfo;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoDto;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoResult;

/**
 * @author chenQF
 * @desc 拼团记录接口
 * @date 2020/8/24 0024 13:53
 */
public interface GroupInfoService extends IService<GroupInfo> {
    /**
     * 查询拼团记录列表
     * @param page
     * @param dto
     * @return
     */
    Page<GroupInfoResult> listGroupInfo(Page<GroupInfoResult> page, GroupInfoDto dto);

    /**
     * 自动下架
     * @param promotionId
     */
    void updateGroupInfo(Integer promotionId);
    
    GroupInfo queryGroupInfoByPromotionId(Integer promotionId);
    
    
    PromotionInfoVo  queryPromotionInfoByGroupInfoId(Integer groupInfoId);
    
}
