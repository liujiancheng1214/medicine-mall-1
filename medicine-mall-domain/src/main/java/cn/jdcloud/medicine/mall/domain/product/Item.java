package cn.jdcloud.medicine.mall.domain.product;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_item")
public class Item extends BaseDomain {

    private Integer id;

    /**商品编码*/
    private String itemNo;

    /**商品名字*/
    private String itemName;

    /**助记码*/
    private String helpCode;

    /**商品分类ID */
    private Integer itemCategoryId;

    /**商品品牌ID*/
    private Integer itemBrandId;

    /**商品剂型ID*/
    private Integer itemDosageId;

    /**商品类型ID*/
    private Integer itemTypeId;

    /**商品规格*/
    private String spec;

    /** 大包装规格*/
    private Integer bigPackage;

    /**中包装规格*/
    private Integer mediumPackage;

    /**生产厂家*/
    private String factory;

    /**产地*/
    private String originPlace;

    /**单位*/
    private String unit;

    /**批准文号*/
    private String approvalNo;

    /**批准文号有效期 */
    private Date approvalNoEndAt;

    /**注册证号*/
    private String regNo;

    /**注册证号有效期*/
    private Date regNoEndAt;

    /**历史最高进价*/
    private BigDecimal highestCostPrice;

    /**历史最低进价*/
    private BigDecimal lowestCostPrice;

    /**历史总采购金额*/
    private BigDecimal subtotalCostAmount;

    /** 历史总销售金额*/
    private BigDecimal subtotalSettAmount;

    /**历史总产生毛利*/
    private BigDecimal subtotalProfit;

    /** 总销售次数*/
    private Integer subtotalSaleTimes;

    /**总销售数量*/
    private Integer subtotalSaleNum;

    /**总采购次数*/
    private Integer subtotalPurTimes;

    /**总采购数量*/
    private Integer subtotalPurNum;

    /**创建人*/
    private Integer creator;

    /**更新人*/
    private Integer updator;

    /**描述*/
    private String remark;

    /**暂估采购金额*/
    private BigDecimal costPrice;
    /**市场价格*/
    private BigDecimal retailPrice;
    /**平台价格*/
    private BigDecimal platformPrice;
//    /**平台团购价格*/
//    private BigDecimal promotionPrice;
    /**生产日期 */
    private Date productionDate;
    // 有效日期  生产日期+有效月
    private Date effectiveDate;
    /**有效期（月） */
    private Integer expiryDate;
    /**库存周转天数 */
    private Integer revolveDay;
    /**日均销量*/
    private Integer saleVolumeAverage;
    /**上市持有人*/
    private String licenseHolder;
    /**是否停购 0否 1是*/
    private Byte stopBuy;
    /**状态 0已上架 1已下架*/
    private Byte state;
    /**重点商品 0否 1是*/
    private Byte important;
    /**进项税*/
    private BigDecimal inTax;
    /**销项税*/
    private BigDecimal outTax;
    /**经营方式(1.直营;2.联营)*/
    private Byte businessModel;
    /**市场体量 */
    private Integer marketValue;
    /**起购价格*/
    private BigDecimal startPurchaseAmount;
    /**政策保护 */
    private Byte protectBy;
    /**销售方式*/
    private Byte saleModel;
    /**采购原因*/
    private Byte purchaseReason;
    /**针对人群*/
    private String forPeople;
    /**销售策略*/
    private String saleStrategy;
    /**效果*/
    private String effect;

    /**商品标签*/
    private String tags;
    /**审核人*/
    private Integer auditor;

    /**审核状态,1为已审核;0为未审核、*/
    private Byte auditState;

    /**存储方式*/
    private String storageMode;
    /**产品封面*/
    private String imgCover;
    /**产品正面 */
    private String imgFront;
    /**产品反面*/
    private String imgReverse;
    /**库存数量*/
    private BigDecimal qty;

    /**最后一次采购时间*/
    private Date lastPurchaseTime;

    /** 最后一次销售时间*/
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
    /**
     * promotion_price
     */
    private BigDecimal promotionPrice;

}
