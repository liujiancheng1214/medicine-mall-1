package cn.jdcloud.medicine.mall.api.biz.promotion.vo;

import cn.jdcloud.medicine.mall.domain.promotion.PromotionItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PromotionItemVo {
    private String itemNo;
    private String itemName;
    private String retailPrice;
    private String promotionPrice;
    private String itemNum;
    private String totalItemType;
    private String totalItemNum;

    public PromotionItemVo() {
    }

    public PromotionItemVo(PromotionItem item) {
        this.itemNo = item.getItemNo();
        this.itemName = item.getItemName();
        this.retailPrice = item.getRetailPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        this.promotionPrice = item.getPromotionPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        this.itemNum = item.getItemNum().toString();
        this.totalItemType = item.getTotalItemType().toString();
        this.totalItemNum = item.getTotalItemNum()==null?null:item.getTotalItemNum().toString();
    }
}
