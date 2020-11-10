package cn.jdcloud.medicine.mall.api.biz.promotion.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.dto.PromotionItemDto;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionItemUserVo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroupItem;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItem;


public interface PromotionGroupItemService extends IService<PromotionGroupItem> {

    void updateByPromotionId(List<PromotionItemDto> promotionItems,Integer promotionId);

    List<PromotionItem>  PromotionGroupItemInfo(@Param("promotionId") Integer promotionId);
    
    PromotionItem promotionItemOne( Integer promotionId,String itemNo);
    
    boolean checkItemIsPromotionItem(String itemNo);
    
    //查询团购列表
    List<ItemVo> listPromotionItems(String itemName,
    		String itemBrandName,Integer sortType);
    
     // 查询某拼团人员信息
    List<PromotionItemUserVo> listPromotionItemUser(Integer promotionId);
}
