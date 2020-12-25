package cn.jdcloud.medicine.mall.api.biz.promotion.service.impl;

import cn.jdcloud.medicine.mall.api.biz.promotion.dto.PromotionDto;
import cn.jdcloud.medicine.mall.api.biz.promotion.dto.PromotionItemDto;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionVo;
import cn.jdcloud.medicine.mall.dao.admin.AdminMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.domain.admin.Admin;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PromotionInfoServiceImpl extends ServiceImpl<PromotionInfoMapper, PromotionInfo> implements PromotionInfoService {
    @Resource
    private PromotionInfoMapper promotionInfoMapper;

    @Resource
    private PromotionGroupItemService promotionGroupItemService;
    @Resource
    AdminMapper adminMapper;
    @Override
    public Page<PromotionVo> page(Page<PromotionInfo> page, QueryWrapper<PromotionInfo> queryWrapper) {
        Page<PromotionInfo> promotionPage = promotionInfoMapper.selectPage(page, queryWrapper);
        Page<PromotionVo> promotionVoPage = new Page<>(promotionPage.getCurrent(), promotionPage.getSize(), promotionPage.getTotal());
        List<PromotionInfo> list = promotionPage.getRecords();
        if(list.size()!=0){
            List<PromotionVo> records = new ArrayList<>();
            HashSet<Integer> userIds = new HashSet<>();
            list.forEach(promotion -> {
                userIds.add(promotion.getAuditor());
                userIds.add(promotion.getUpdator());
                userIds.add(promotion.getCreator());
            });
            Map<Integer, String> admins = adminMapper.selectList(new QueryWrapper<Admin>().in("id", userIds))
                    .stream().collect(Collectors.toMap(Admin::getId, Admin::getName));
            list.forEach(promotion -> records.add(new PromotionVo(promotion,admins)));
            promotionVoPage.setRecords(records);
        }
        return promotionVoPage;
    }

    @Override
    public boolean save(PromotionDto promotionDto, Integer userId) {
        PromotionInfo promotionInfo = promotionDto.createPromotionInfo();
        if (promotionInfo.getPromotionId() == null) {
            promotionInfo.setCreator(userId);
            promotionInfo.setCreateTime(new Date());
        }
        promotionInfo.setUpdator(userId);
        boolean saveOrUpdate = this.saveOrUpdate(promotionInfo);
        if(saveOrUpdate){
            Integer promotionId = promotionInfo.getPromotionId();
            List<PromotionItemDto> promotionItems = promotionDto.getItems();
            if (promotionInfo.getPromotionType()==1){
                promotionGroupItemService.updateByPromotionId(promotionItems,promotionId);
            }
        }
        return saveOrUpdate;
    }

    @Override
    public PromotionInfoResult getOne(Integer promotionId) {
        return promotionInfoMapper.getOne(promotionId);
    }
}
