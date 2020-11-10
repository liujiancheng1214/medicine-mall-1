package cn.jdcloud.medicine.mall.domain.promotion;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PromotionItem {
    /**活动id*/
    private Integer promotionId;
    private String itemName;
    private BigDecimal retailPrice;
    private BigDecimal promotionPrice;
    /**商品编码*/
    private String itemNo;
    /**单份数量*/
    private Integer itemNum;
    /**活动参与数量类别(1:按实际库存;2:按固定数量)*/
    private Byte totalItemType;
    /**参与活动商品总数量*/
    private Integer totalItemNum;
    /**参与活动商品剩余数量*/
    private Integer totalItemQty;

}
