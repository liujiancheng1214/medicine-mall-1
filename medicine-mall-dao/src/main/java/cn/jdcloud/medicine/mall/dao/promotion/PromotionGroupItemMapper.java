package cn.jdcloud.medicine.mall.dao.promotion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroupItem;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItem;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItemListDto;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItemUserDto;

public interface PromotionGroupItemMapper extends BaseMapper<PromotionGroupItem> {
    void insertBatch(@Param("list") List<PromotionGroupItem> itemList);

    List<PromotionItem>  PromotionGroupItemInfo(@Param("promotionId") Integer promotionId);
    
    List<PromotionItemListDto> listPromotionItems(@Param("itemNo")String itemNo,@Param("itemName")String itemName,
    		@Param("itemBrandName")String itemBrandName
    		,@Param("sortType")Integer sortType,@Param("promotionIds")List<Integer>promotionIds);
    
    
    List<PromotionItemUserDto> listPromotionItemUser(@Param("promotionId") Integer promotionId);
    
    int checkItemIsPromotionItem(@Param("itemNo")String itemNo);
}