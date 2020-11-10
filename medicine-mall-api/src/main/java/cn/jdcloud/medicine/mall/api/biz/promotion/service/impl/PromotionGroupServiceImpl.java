package cn.jdcloud.medicine.mall.api.biz.promotion.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupService;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionGroupMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroup;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenQF
 * @desc 团购模板业务层
 * @date 2020/8/22 0022 14:23
 */
@Service
public class PromotionGroupServiceImpl extends ServiceImpl<PromotionGroupMapper, PromotionGroup>
        implements PromotionGroupService {

    @Resource
    PromotionGroupMapper promotionGroupMapper;

    @Resource
    PromotionInfoMapper promotionInfoMapper;

    @Override
    public Page<PromotionGroup> listGroup(Page<PromotionGroup> page) {
        return this.page(page,null);
    }

    @Override
    public void deleteGroup(Integer id) {
        //如果有活动正在用次模板则不允许删除
        Map map = new HashMap();
        map.put("rule_id",id);
        List<PromotionInfo> list = promotionInfoMapper.selectByMap(map);
        if(list.size() > 0){
            throw new ApiException(1000, "该活动模板正被其他活动使用中");
        }
        int count = promotionGroupMapper.deleteById(id);
        if(count <= 0){
            throw new ApiException(1000, "等级删除失败");
        }
    }

    @Override
    public List<PromotionGroup> getAll() {
        return promotionGroupMapper.selectList(null);
    }

}
