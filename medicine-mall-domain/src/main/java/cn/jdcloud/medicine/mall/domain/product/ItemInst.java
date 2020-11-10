package cn.jdcloud.medicine.mall.domain.product;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;



@Data
public class ItemInst {
    private Integer id;

    private String itemNo;

    private String itemName;

    private String helpCode;

    private Integer itemCategoryId;

    private Integer itemBrandId;

    private Integer itemDosageId;

    private Integer itemTypeId;

    private String spec;

    private Integer bigPackage;

    private Integer mediumPackage;

    private String factory;

    private String originPlace;

    private String unit;

    private String approvalNo;

    private Date approvalNoEndAt;

    private String regNo;

    private Date regNoEndAt;

    private BigDecimal highestCostPrice;

    private BigDecimal lowestCostPrice;

    private BigDecimal subtotalCostAmount;

    private BigDecimal subtotalSettAmount;

    private BigDecimal subtotalProfit;

    private Integer subtotalSaleTimes;

    private Integer subtotalSaleNum;

    private Integer subtotalPurTimes;

    private Integer subtotalPurNum;

    private Integer creator;

    private Date createTime;

    private Integer updator;

    private Date updateTime;

    private String remark;

    private BigDecimal costPrice;

    private BigDecimal retailPrice;

    private Integer revolveDay;

    private Integer saleVolumeAverage;

    private String licenseHolder;

    private Byte stopBuy;

    private Byte state;

    private Byte important;

    private BigDecimal inTax;

    private BigDecimal outTax;

    private Integer expiryDate;

    private Byte businessModel;

    private Integer marketValue;

    private BigDecimal startPurchaseAmount;

    private Byte protectBy;

    private Byte saleModel;

    private Byte purchaseReason;

    private String forPeople;

    private Integer auditor;

    private Byte auditState;

    private String storageMode;

    private String imgCover;

    private String imgFront;

    private String imgReverse;

    private BigDecimal qty;

    private Date lastPurchaseTime;

    private Date lastSaleTime;
    
    // 功能主治
    private String indications;
    // 主要成分
    private String components;
    // 用法用量
    private String dosage;
    // 不良反应
    private  String adverseReactions;
    // 注意事项
    private String attention;
    // 禁忌
    private  String taboo;

    private Boolean isDeleted;

    private Integer orderId;

    private Integer userId;

    private Integer status;

   
}