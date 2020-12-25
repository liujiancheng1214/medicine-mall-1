package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItemListDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品Item")
public class ItemVo{
    private Integer id;
    /**商品编码*/
    @ApiModelProperty(value="ERP_ID")
    private String erpId;
    @ApiModelProperty(value="商品编码")
    private String itemNo;
    /**商品名字*/
    @ApiModelProperty(value="商品名字")
    private String itemName;
    /**助记码*/
    @ApiModelProperty(value="助记码")
    private String helpCode;
    /**商品分类ID */
    @ApiModelProperty(value="String")
    private Integer itemCategoryId;
    /**商品品牌ID*/
    @ApiModelProperty(value="商品品牌ID")
    private Integer itemBrandId;
    @ApiModelProperty(value="商品品牌")
    private String itemBrandName;
    /**商品剂型ID*/
    @ApiModelProperty(value="商品剂型ID")
    private Integer itemDosageId;
    /**商品类型ID*/
    @ApiModelProperty(value="商品类型ID")
    private Integer itemTypeId;
    /**商品规格*/
    @ApiModelProperty(value="商品规格")
    private String spec;
    /** 大包装规格*/
    @ApiModelProperty(value="大包装规格")
    private Integer bigPackage;
    /**中包装规格*/
    @ApiModelProperty(value="中包装规格")
    private Integer mediumPackage;
    /**生产厂家*/
    @ApiModelProperty(value="生产厂家")
    private String factory;
    /**产地*/
    @ApiModelProperty(value="产地")
    private String originPlace;
    /**单位*/
    @ApiModelProperty(value="单位")
    private String unit;
    /**平台价格*/
    @ApiModelProperty(value="平台价格")
    private BigDecimal platformPrice;
    /**生产日期 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value="生产日期")
    private Date productionDate;
    // 有效日期  生产日期+有效月
    @ApiModelProperty(value="有效日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date effectiveDate;
    /**批准文号*/
    @ApiModelProperty(value="批准文号")
    private String approvalNo;
    /**批准文号有效期 */
    @ApiModelProperty(value="批准文号有效期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date approvalNoEndAt;
    /**注册证号*/
    @ApiModelProperty(value="注册证号")
    private String regNo;
    /**注册证号有效期*/
    @ApiModelProperty(value="注册证号有效期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date regNoEndAt;
    /**历史最高进价*/
    @ApiModelProperty(value="历史最高进价")
    private BigDecimal highestCostPrice;
    /**历史最低进价*/
    @ApiModelProperty(value="历史最低进价")
    private BigDecimal lowestCostPrice;
    /**历史总采购金额*/
    @ApiModelProperty(value="历史总采购金额")
    private BigDecimal subtotalCostAmount;
    /** 历史总销售金额*/
    @ApiModelProperty(value=" 历史总销售金额")
    private BigDecimal subtotalSettAmount;
    /**历史总产生毛利*/
    @ApiModelProperty(value="历史总产生毛利")
    private BigDecimal subtotalProfit;
    /** 总销售次数*/
    @ApiModelProperty(value="总销售次数")
    private Integer subtotalSaleTimes;
    /**总销售数量*/
    @ApiModelProperty(value="总销售数量")
    private Integer subtotalSaleNum;
    /**总采购次数*/
    @ApiModelProperty(value="总采购次数")
    private Integer subtotalPurTimes;
    /**总采购数量*/
    @ApiModelProperty(value="总采购数量")
    private Integer subtotalPurNum;
    /**创建人*/
    @ApiModelProperty(value="创建人")
    private Integer creator;
    /**创建时间*/
    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    /**更新人*/
    @ApiModelProperty(value="更新人")
    private Integer updator;
    /**更新时间*/
    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;
    /**描述*/
    @ApiModelProperty(value="描述")
    private String remark;
    /**暂估采购金额*/
    @ApiModelProperty(value="暂估采购金额")
    private BigDecimal costPrice;
    /**零售价格*/
    @ApiModelProperty(value="零售价格")
    private BigDecimal retailPrice;

    @ApiModelProperty(value="团购价格")
    private BigDecimal promotionPrice;
    /**库存周转天数 */
    @ApiModelProperty(value="库存周转天数")
    private Integer revolveDay;
    /**日均销量*/
    @ApiModelProperty(value="日均销量")
    private Integer saleVolumeAverage;
    /**上市持有人*/
    @ApiModelProperty(value="上市持有人")
    private String licenseHolder;
    /**是否停购*/
    @ApiModelProperty(value="是否停购")
    private Byte stopBuy;
    /**状态 0已上架 1已下架*/
    @ApiModelProperty(value="状态 0已上架 1已下架")
    private Byte state;
    /**重点商品*/
    @ApiModelProperty(value="重点商品")
    private Byte important;
    /**进项税*/
    @ApiModelProperty(value="进项税")
    private BigDecimal inTax;
    /**销项税*/
    @ApiModelProperty(value="销项税")
    private BigDecimal outTax;
    /**有效期（月） */
    @ApiModelProperty(value="有效期（月）")
    private Integer expiryDate;
    /**经营方式(1.直营;2.联营)*/
    @ApiModelProperty(value="经营方式(1.直营;2.联营)")
    private Byte businessModel;
    /**市场体量 */
    @ApiModelProperty(value="市场体量")
    private Integer marketValue;
    /**起购价格*/
    @ApiModelProperty(value="起购价格")
    private BigDecimal startPurchaseAmount;
    /**政策保护 */
    @ApiModelProperty(value="政策保护")
    private Byte protectBy;
    /**销售方式*/
    @ApiModelProperty(value="销售方式")
    private Byte saleModel;
    /**采购原因*/
    @ApiModelProperty(value="采购原因")
    private Byte purchaseReason;
    /**针对人群*/
    @ApiModelProperty(value="针对人群")
    private String forPeople;
    /**销售策略*/
    @ApiModelProperty(value="销售策略")
    private String saleStrategy;
    /**效果*/
    @ApiModelProperty(value="效果")
    private String effect;
    /**商品标签*/
    @ApiModelProperty(value="商品标签")
    private String tags;
    /**审核人*/
    @ApiModelProperty(value="审核人")
    private Integer auditor;
    /**审核状态,1为已审核;0为未审核、*/
    @ApiModelProperty(value="审核状态,1为已审核;0为未审核")
    private Byte auditState;
    /**存储方式*/
    @ApiModelProperty(value="存储方式")
    private String storageMode;
    /**产品封面*/
    @ApiModelProperty(value="产品封面")
    private String imgCover;
    /**产品正面 */
    @ApiModelProperty(value="产品正面")
    private String imgFront;
    /**产品反面*/
    @ApiModelProperty(value="产品反面")
    private String imgReverse;
    /**库存数量*/
    @ApiModelProperty(value="库存数量")
    private BigDecimal qty;
    /**最后一次采购时间*/
    @ApiModelProperty(value="最后一次采购时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date lastPurchaseTime;
    /** 最后一次销售时间*/
    @ApiModelProperty(value="最后一次销售时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date lastSaleTime;
    @ApiModelProperty(value="产品批次")
    private List<ItemBatchVo> itemBatch;
    // 功能主治
    @ApiModelProperty(value="功能主治")
    private String indications;
    // 主要成分
    @ApiModelProperty(value="主要成分")
    private String components;
    // 用法用量
    @ApiModelProperty(value="用法用量")
    private String dosage;
    // 不良反应
    @ApiModelProperty(value="不良反应")
    private  String adverseReactions;
    // 注意事项
    @ApiModelProperty(value="注意事项")
    private String attention;
    // 禁忌
    @ApiModelProperty(value="禁忌")
    private  String taboo;
    @ApiModelProperty(value="是否团购商品 1 是 、 0 不是")
    private int promotionTag;

    private Integer  promotionId;

    public ItemVo() { }

    public ItemVo(PromotionItemListDto promotionItemListDto) {
    	this.id=promotionItemListDto.getItemId();
    	this.promotionId=promotionItemListDto.getPromotionId();
    	this.itemNo=promotionItemListDto.getItemNo();
    	this.itemName=promotionItemListDto.getItemName();
    	this.imgCover=promotionItemListDto.getImgCover();
    	this.effectiveDate=promotionItemListDto.getEffectiveDate();
    	this.subtotalSaleNum=promotionItemListDto.getSubtotalSaleNum();
        this.promotionPrice=promotionItemListDto.getItemGroupPrice();
    	this.platformPrice=promotionItemListDto.getItemGroupPrice();
    	this.retailPrice=promotionItemListDto.getRetailPrice();
    	this.factory=promotionItemListDto.getFactory();
    	this.promotionId=promotionItemListDto.getPromotionId();
    	this.promotionTag=1;
    }


    public ItemVo(Item item, Map<String, List<ItemBatchVo>> map) {
        this.id = item.getId();
        this.itemNo = item.getItemNo();
        this.itemName = item.getItemName();
        this.helpCode = item.getHelpCode();
        this.itemCategoryId = item.getItemCategoryId();
        this.itemBrandId = item.getItemBrandId();
        this.itemDosageId = item.getItemDosageId();
        this.itemTypeId = item.getItemTypeId();
        this.spec = item.getSpec();
        this.bigPackage = item.getBigPackage();
        this.mediumPackage = item.getMediumPackage();
        this.factory = item.getFactory();
        this.originPlace = item.getOriginPlace();
        this.unit = item.getUnit();
        this.approvalNo = item.getApprovalNo();
        this.approvalNoEndAt = item.getApprovalNoEndAt();
        this.regNo = item.getRegNo();
        this.regNoEndAt = item.getRegNoEndAt();
        this.highestCostPrice = item.getHighestCostPrice();
        this.lowestCostPrice = item.getLowestCostPrice();
        this.subtotalCostAmount = item.getSubtotalCostAmount();
        this.subtotalSettAmount = item.getSubtotalSettAmount();
        this.subtotalProfit = item.getSubtotalProfit();
        this.subtotalSaleTimes = item.getSubtotalSaleTimes();
        this.subtotalSaleNum = item.getSubtotalSaleNum();
        this.subtotalPurTimes = item.getSubtotalPurTimes();
        this.subtotalPurNum = item.getSubtotalPurNum();
        this.creator = item.getCreator();
        this.createTime = item.getCreateTime();
        this.updator = item.getUpdator();
        this.updateTime = item.getUpdateTime();
        this.remark = item.getRemark();
        this.costPrice = item.getCostPrice();
        this.retailPrice = item.getRetailPrice();
        this.revolveDay = item.getRevolveDay();
        this.saleVolumeAverage = item.getSaleVolumeAverage();
        this.licenseHolder = item.getLicenseHolder();
        this.stopBuy = item.getStopBuy();
        this.state = item.getState();
        this.important = item.getImportant();
        this.inTax = item.getInTax();
        this.outTax = item.getOutTax();
        this.expiryDate = item.getExpiryDate();
        this.businessModel = item.getBusinessModel();
        this.marketValue = item.getMarketValue();
        this.startPurchaseAmount = item.getStartPurchaseAmount();
        this.protectBy = item.getProtectBy();
        this.saleModel = item.getSaleModel();
        this.purchaseReason = item.getPurchaseReason();
        this.forPeople = item.getForPeople();
        this.saleStrategy = item.getSaleStrategy();
        this.effect = item.getEffect();
        this.tags = item.getTags();
        this.auditor = item.getAuditor();
        this.auditState = item.getAuditState();
        this.storageMode = item.getStorageMode();
        this.imgCover = item.getImgCover();
        this.imgFront = item.getImgFront();
        this.imgReverse = item.getImgReverse();
        this.qty = item.getQty();
        this.lastPurchaseTime = item.getLastPurchaseTime();
        this.lastSaleTime = item.getLastSaleTime();
        this.itemBatch = map.get(this.itemNo);
        this.platformPrice = item.getPlatformPrice();
        this.productionDate=item.getProductionDate();
        this.indications=item.getIndications();
        this.components=item.getComponents();
        this.dosage=item.getDosage();
        this.adverseReactions=item.getAdverseReactions();
        this.attention=item.getAttention();
        this.taboo=item.getTaboo();
        this.erpId = item.getErpId();
    }
}
