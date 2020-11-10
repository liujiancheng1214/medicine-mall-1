package cn.jdcloud.medicine.mall.api.biz.product.excel;

import cn.jdcloud.medicine.mall.api.annotation.ExcelHead;
import cn.jdcloud.medicine.mall.domain.product.Item;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 商品excel类
 */
@Data
public class ItemExcel {

    @ExcelHead(value = "商品ID", isImport = false)
    private Integer id;
    /**
     * 商品编码
     */
    @ExcelHead(value = "商品编码",notNull = true)
    private String itemNo;
    /**
     * 商品名字
     */
    @ExcelHead(value = "商品名字",notNull = true)
    private String itemName;
    /**
     * 助记码
     */
    @ExcelHead(value = "助记码")
    private String helpCode;
    /**
     * 商品分类
     */
    @ExcelHead(value = "商品分类",notNull = true,isOption = true)
    private Integer itemCategoryId;
    /**
     * 商品品牌
     */
    @ExcelHead(value = "商品品牌",notNull = true,isOption = true)
    private Integer itemBrandId;
    /**
     * 商品剂型ID
     */
    @ExcelHead(value = "商品剂型", isImport = false,isOption = true)
    private Integer itemDosageId;
    /**
     * 商品类型ID
     */
    @ExcelHead(value = "商品类型", isImport = false,isOption = true)
    private Integer itemTypeId;
    /**
     * 商品规格
     */
    @ExcelHead(value = "商品规格",notNull = true)
    private String spec;
    /**
     * 大包装规格
     */
    @ExcelHead(value = "大包装规格",notNull = true)
    private Integer bigPackage;
    /**
     * 中包装规格
     */
    @ExcelHead(value = "中包装规格")
    private Integer mediumPackage;
    /**
     * 生产厂家
     */
    @ExcelHead(value = "生产厂家",notNull = true)
    private String factory;
    /**
     * 产地
     */
    @ExcelHead(value = "产地")
    private String originPlace;
    /**
     * 单位
     */
    @ExcelHead(value = "单位",notNull = true)
    private String unit;
    /**
     * 批准文号
     */
    @ExcelHead(value = "批准文号")
    private String approvalNo;
    /**
     * 批准文号有效期
     */
    @ExcelHead(value = "批准文号有效期")
    private Date approvalNoEndAt;
    /**
     * 注册证号
     */
    @ExcelHead(value = "注册证号")
    private String regNo;
    /**
     * 注册证号有效期
     */
    @ExcelHead(value = "注册证号有效期")
    private Date regNoEndAt;
    /**
     * 历史最高进价
     */
    @ExcelHead(value = "历史最高进价", isImport = false)
    private BigDecimal highestCostPrice;
    /**
     * 历史最低进价
     */
    @ExcelHead(value = "历史最低进价", isImport = false)
    private BigDecimal lowestCostPrice;
    /**
     * 历史总采购金额
     */
    @ExcelHead(value = "历史总采购金额", isImport = false)
    private BigDecimal subtotalCostAmount;
    /**
     * 历史总销售金额
     */
    @ExcelHead(value = "历史总销售金额", isImport = false)
    private BigDecimal subtotalSettAmount;
    /**
     * 历史总产生毛利
     */
    @ExcelHead(value = "历史总产生毛利", isImport = false)
    private BigDecimal subtotalProfit;
    /**
     * 总销售次数
     */
    @ExcelHead(value = "总销售次数", isImport = false)
    private Integer subtotalSaleTimes;
    /**
     * 总销售数量
     */
    @ExcelHead(value = "总销售数量", isImport = false)
    private Integer subtotalSaleNum;
    /**
     * 总采购次数
     */
    @ExcelHead(value = "总采购次数", isImport = false)
    private Integer subtotalPurTimes;
    /**
     * 总采购数量
     */
    @ExcelHead(value = "总采购数量", isImport = false)
    private Integer subtotalPurNum;
    /**
     * 创建人
     */
    @ExcelHead(value = "创建人", isImport = false)
    private String creator;
    /**
     * 创建时间
     */
    @ExcelHead(value = "创建时间", isImport = false,datePattern="yyyy/MM/dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @ExcelHead(value = "更新人", isImport = false)
    private String updator;
    /**
     * 更新时间
     */
    @ExcelHead(value = "更新时间", isImport = false,datePattern="yyyy/MM/dd HH:mm:ss")
    private Date updateTime;
    /**
     * 描述
     */
    @ExcelHead(value = "描述")
    private String remark;
    /**
     * 暂估采购金额
     */
    @ExcelHead(value = "暂估采购金额")
    private BigDecimal costPrice;
    /**
     * 零售价格
     */
    @ExcelHead(value = "零售价格",notNull = true)
    private BigDecimal retailPrice;
    /**
     * 库存周转天数
     */
    @ExcelHead(value = "库存周转天数")
    private Integer revolveDay;
    /**
     * 日均销量
     */
    @ExcelHead(value = "日均销量")
    private Integer saleVolumeAverage;
    /**
     * 上市持有人
     */
    @ExcelHead(value = "上市持有人")
    private String licenseHolder;
    /**
     * 是否停购
     */
    @ExcelHead(value = "是否停购",notNull = true,isOption = true)
    private Byte stopBuy;
    /**
     * 状态 0已上架 1已下架
     */
    @ExcelHead(value = "商品状态",isImport = false)
    private String state;
    /**
     * 重点商品
     */
    @ExcelHead(value = "重点商品",notNull = true,isOption = true)
    private Byte important;
    /**
     * 进项税
     */
    @ExcelHead(value = "进项税")
    private BigDecimal inTax;
    /**
     * 销项税
     */
    @ExcelHead(value = "销项税")
    private BigDecimal outTax;
    /**
     * 有效期（月）
     */
    @ExcelHead(value = "有效期（月）",notNull = true)
    private Integer expiryDate;
    /**
     * 经营方式(1.直营;2.联营0)
     */
    @ExcelHead(value = "经营方式",notNull = true,isOption = true)
    private Byte businessModel;
    /**
     * 市场体量
     */
    @ExcelHead(value = "市场体量", isImport = false)
    private Integer marketValue;
    /**
     * 起购价格
     */
    @ExcelHead(value = "起购价格",notNull = true)
    private BigDecimal startPurchaseAmount;
    /**
     * 政策保护
     */
    @ExcelHead(value = "政策保护", isImport = false)
    private Byte protectBy;
    /**
     * 销售方式
     */
    @ExcelHead(value = "销售方式", isImport = false)
    private Byte saleModel;
    /**
     * 采购原因
     */
    @ExcelHead(value = "采购原因", isImport = false)
    private Byte purchaseReason;
    /**
     * 针对人群
     */
    @ExcelHead(value = "针对人群")
    private String forPeople;
    /**
     * 销售策略
     */
    @ExcelHead(value = "销售策略")
    private String saleStrategy;
    /**
     * 效果
     */
    @ExcelHead(value = "效果")
    private String effect;
    /**
     * 商品标签
     */
    @ExcelHead(value = "商品标签")
    private String tags;
    /**
     * 审核人
     */
    @ExcelHead(value = "审核人", isImport = false)
    private String auditor;
    /**
     * 审核状态,1为已审核;0为未审核、
     */
    @ExcelHead(value = "审核状态", isImport = false)
    private String auditState;
    /**
     * 存储方式
     */
    @ExcelHead(value = "存储方式")
    private String storageMode;
    /**
     * 库存数量
     */
    @ExcelHead(value = "库存数量", isImport = false)
    private BigDecimal qty;
    /**
     * 最后一次采购时间
     */
    @ExcelHead(value = "最后一次采购时间", isImport = false)
    private Date lastPurchaseTime;
    /**
     * 最后一次销售时间
     */
    @ExcelHead(value = "最后一次销售时间", isImport = false)
    private Date lastSaleTime;

    public ItemExcel() {}

    public ItemExcel(Item item, Map<Integer,String> users) {
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
        this.creator = users.get(item.getCreator()) ;
        this.createTime = item.getCreateTime();
        this.updator = users.get(item.getUpdator());
        this.updateTime = item.getUpdateTime();
        this.remark = item.getRemark();
        this.costPrice = item.getCostPrice();
        this.retailPrice = item.getRetailPrice();
        this.revolveDay = item.getRevolveDay();
        this.saleVolumeAverage = item.getSaleVolumeAverage();
        this.licenseHolder = item.getLicenseHolder();
        this.stopBuy = item.getStopBuy();
        this.state = item.getState() == 0 ? "上架" : "下架";
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
        this.auditor = users.get(item.getAuditor());
        this.auditState = item.getAuditState() == 0 ? "未审核" : "已审核";
        this.storageMode = item.getStorageMode();
        this.qty = item.getQty();
        this.lastPurchaseTime = item.getLastPurchaseTime();
        this.lastSaleTime = item.getLastSaleTime();
    }

    public Item createPojo(){
        Item item = new Item();
        item.setItemNo(this.itemNo);
        item.setItemName(this.itemName);
        item.setHelpCode(this.helpCode);
        item.setItemCategoryId(this.itemCategoryId);
        item.setItemBrandId(this.itemBrandId);
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
        item.setSaleVolumeAverage(this.saleVolumeAverage);
        item.setLicenseHolder(this.licenseHolder);
        item.setStopBuy(this.stopBuy);
        item.setImportant(this.important);
        item.setInTax(this.inTax);
        item.setOutTax(this.outTax);
        item.setExpiryDate(this.expiryDate);
        item.setBusinessModel(this.businessModel);
        item.setStartPurchaseAmount(this.startPurchaseAmount);
        item.setForPeople(this.forPeople);
        item.setSaleStrategy(this.saleStrategy);
        item.setEffect(this.effect);
        item.setTags(this.tags);
        item.setStorageMode(this.storageMode);
        return item;
    }
}