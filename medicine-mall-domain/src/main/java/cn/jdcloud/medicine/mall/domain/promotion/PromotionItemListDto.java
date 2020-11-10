package cn.jdcloud.medicine.mall.domain.promotion;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class PromotionItemListDto {
     private Integer promotionId;
     private Integer itemId;
     private String itemNo;
     private String itemName;
     private String imgCover;
     private Integer subtotalSaleNum;
     private BigDecimal retailPrice;
     private BigDecimal itemGroupPrice;
     private Date effectiveDate;
     private String factory;
}
