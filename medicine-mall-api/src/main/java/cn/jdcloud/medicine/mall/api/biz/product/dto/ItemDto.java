package cn.jdcloud.medicine.mall.api.biz.product.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import lombok.Data;

@Data
public class ItemDto {

    private Integer id;
    /**
     * 商品编码
     */
    private String itemNo;
    /**
     * 商品名字
     */
    private String itemName;
    /**
     * 助记码
     */
    private String helpCode;
    /**
     * 商品分类ID
     */
    private Integer itemCategoryId;
    /**
     * 商品品牌ID
     */
    private Integer itemBrandId;
    /**
     * 商品剂型ID
     */
    private Integer itemDosageId;
    /**
     * 商品类型ID
     */
    private Integer itemTypeId;
    /**
     * 商品规格
     */
    private String spec;
    /**
     * 大包装规格
     */
    private Integer bigPackage;
    /**
     * 中包装规格
     */
    private Integer mediumPackage;
    /**
     * 生产厂家
     */
    private String factory;
    /**
     * 产地
     */
    private String originPlace;
    /**
     * 单位
     */
    private String unit;
    /**
     * 批准文号
     */
    private String approvalNo;
    /**
     * 批准文号有效期
     */
    private Date approvalNoEndAt;
    /**
     * 注册证号
     */
    private String regNo;
    /**
     * 注册证号有效期
     */
    private Date regNoEndAt;
    /**
     * 描述
     */
    private String remark;
    /**
     * 暂估采购金额
     */
    private BigDecimal costPrice;
    /**
     * 零售价格
     */
    private BigDecimal retailPrice;
    /**
     * 库存周转天数
     */
    private Integer revolveDay;
    /**
     * 上市持有人
     */
    private String licenseHolder;
    /**
     * 是否停购
     */
    private Byte stopBuy;
    /**
     * 状态 0已上架 1已下架
     */
    private Byte state;
    /**
     * 重点商品
     */
    private Byte important;
    /**
     * 进项税
     */
    private BigDecimal inTax;
    /**
     * 销项税
     */
    private BigDecimal outTax;
    /**
     * 有效期（月）
     */
    private Integer expiryDate;
    
    /**生产日期 */
    private Date productionDate;
    
    /**
     * 经营方式(1.直营;2.联营)
     */
    private Byte businessModel;
    /**
     * 市场体量
     */
    private Integer marketValue;
    /**
     * 起购价格
     */
    private BigDecimal startPurchaseAmount;
    /**
     * 政策保护
     */
    private Byte protectBy;
    /**
     * 销售方式
     */
    private Byte saleModel;
    /**
     * 采购原因
     */
    private Byte purchaseReason;
    /**
     * 针对人群
     */
    private String forPeople;
    /**
     * 销售策略
     */
    private String saleStrategy;
    /**
     * 效果
     */
    private String effect;
    /**
     * 商品标签
     */
    private String tags;
    /**
     * 审核人
     */
    private Integer auditor;
    /**
     * 审核状态,1为已审核;0为未审核、
     */
    private Byte auditState;
    /**
     * 存储方式
     */
    private String storageMode;
    /**
     * 产品封面
     */
    private String imgCover;
    /**
     * 产品正面
     */
    private String imgFront;
    /**
     * 产品反面
     */
    private String imgReverse;
    /**
     * 库存数量
     */
    private BigDecimal qty;
    
    
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
    
    /**平台价格*/
    private BigDecimal platformPrice;

    private List<ItemBatch> itemBatch;

    public Item wrapItem() {
        Item item = new Item();
        item.setId(this.id);
        item.setPlatformPrice(this.platformPrice);
        item.setItemNo(this.itemNo);
        item.setItemName(this.itemName);
        item.setHelpCode(this.helpCode);
        item.setItemCategoryId(this.itemCategoryId);
        item.setItemBrandId(this.itemBrandId);
        item.setItemDosageId(this.itemDosageId);
        item.setItemTypeId(this.itemTypeId);
        item.setSpec(this.spec);
        item.setBigPackage(this.bigPackage);
        item.setMediumPackage(this.mediumPackage);
        item.setFactory(this.factory);
        item.setOriginPlace(this.originPlace);
        item.setUnit(this.unit);
        item.setApprovalNo(this.approvalNo);
        item.setApprovalNoEndAt(this.approvalNoEndAt);
        item.setRegNo(this.regNo);
        item.setRegNoEndAt(this.regNoEndAt);
        item.setRemark(this.remark);
        item.setCostPrice(this.costPrice);
        item.setRetailPrice(this.retailPrice);
        item.setRevolveDay(this.revolveDay);
        item.setLicenseHolder(this.licenseHolder);
        item.setStopBuy(this.stopBuy);
        item.setState(this.state);
        item.setImportant(this.important);
        item.setInTax(this.inTax);
        item.setOutTax(this.outTax);
        item.setExpiryDate(this.expiryDate);
        item.setBusinessModel(this.businessModel);
        item.setMarketValue(this.marketValue);
        item.setStartPurchaseAmount(this.startPurchaseAmount);
        item.setProtectBy(this.protectBy);
        item.setSaleModel(this.saleModel);
        item.setPurchaseReason(this.purchaseReason);
        item.setForPeople(this.forPeople);
        item.setSaleStrategy(this.saleStrategy);
        item.setEffect(this.effect);
        item.setTags(this.tags);
        item.setAuditor(this.auditor);
        item.setAuditState(this.auditState);
        item.setStorageMode(this.storageMode);
        item.setImgCover(this.imgCover);
        item.setImgFront(this.imgFront);
        item.setImgReverse(this.imgReverse);
        item.setQty(this.qty);
        item.setProductionDate(this.productionDate);
        item.setIndications(this.indications);
        item.setComponents(this.components);
        item.setDosage(this.dosage);
        item.setAdverseReactions(this.adverseReactions);
        item.setAttention(this.attention);
        item.setTaboo(this.taboo);
        // 有效期计算
        // 有效日期  生产日期+有效月
        item.setEffectiveDate(addMount(this.productionDate,this.expiryDate));
        return item;
    }
    
    
    private Date addMount(Date  d,int  mount) {
    	Date  date=new Date();
    	date.setYear(d.getYear());
    	date.setMonth(d.getMonth()+mount);
    	date.setDate(d.getDate());
    	return date;
    }
   
    
    
    
}