package cn.jdcloud.medicine.mall.api.biz.promotion.service;

import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chenQF
 * @desc 团购模板接口
 * @date 2020/8/22 0022 14:23
 */
public interface PromotionGroupService extends IService<PromotionGroup> {
    /**
     * 查询模板列表
     * @param page
     * @return
     */
    Page<PromotionGroup> listGroup(Page<PromotionGroup> page);

    /**
     * 删除模板
     * @param id
     */
    void deleteGroup(Integer id);

    /**
     * 查询所有模板（下拉框）
     * @return
     */
    List<PromotionGroup> getAll();
}
