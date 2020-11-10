package cn.jdcloud.medicine.mall.api.biz.promotion.dto;

import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroupItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PromotionItemDto {
    private String itemNo;
    private BigDecimal promotionPrice;
    private Integer itemNum;
    private Byte totalItemType;
    private Integer totalItemNum;

    public PromotionGroupItem createGroup(){
        PromotionGroupItem groupItem = new PromotionGroupItem();
        groupItem.setItemGroupPrice(this.promotionPrice);
        groupItem.setItemNo(this.itemNo);
        groupItem.setItemNum(this.itemNum);
        groupItem.setTotalItemType(this.totalItemType);
        groupItem.setTotalItemNum(this.totalItemNum);
        groupItem.setTotalItemQty(this.totalItemNum);
        return groupItem;
    }
}
