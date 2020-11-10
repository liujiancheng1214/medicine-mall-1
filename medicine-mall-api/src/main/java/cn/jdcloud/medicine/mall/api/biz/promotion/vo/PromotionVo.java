package cn.jdcloud.medicine.mall.api.biz.promotion.vo;

import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class PromotionVo {
    private Integer promotionId;
    /**促销方案名称*/
    private String promotionName;
    /**允许指定等级的客户参与本活动（多个等级用,隔开）*/
    private String containerBusinessLevel;
    /**分享时候的标题*/
    private String shareTitle;
    /**商家PLUS独享(1:独享；2非独享)*/
    private Byte specialBusinessPlus;
    /**是否可以使用优惠券(1:使用;2:不使用)*/
    private Byte useCouponState;
    /**反佣金状态(1:返佣;2:不返佣)*/
    private Byte brokerGetState;
    /**审核状态（1:已审核;2:未审核）*/
    private Byte auditStatus;
    /**活动状态*/
    private Byte status;
    /**是否限购(1:限购;2不限购)*/
    private Byte quotaBuyState;
    /**是否给与积分(1:给予；2不给于)*/
    private Byte givePointState;
    /**每人配额(几份)*/
    private Integer quotaUserNum;
    /**活动开始时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date beginTime;
    /**活动结束时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    /**创建人*/
    private String creator;
    /**更新人*/
    private String updator;
    /**审核员*/
    private String auditor;
    /**促销类型 (1:团购)*/
    private Byte promotionType;
    /**目标类型的规则Id(团购、买赠、打折）*/
    private Integer ruleId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public PromotionVo() {
    }

    public PromotionVo(PromotionInfo promotion, Map<Integer, String> admins) {
        this.promotionId = promotion.getPromotionId();
        this.promotionName = promotion.getPromotionName();
        this.containerBusinessLevel = promotion.getContainerBusinessLevel();
        this.shareTitle = promotion.getShareTitle();
        this.specialBusinessPlus = promotion.getSpecialBusinessPlus();
        this.useCouponState = promotion.getUseCouponState();
        this.brokerGetState = promotion.getBrokerGetState();
        this.auditStatus = promotion.getAuditStatus();
        this.status = promotion.getStatus();
        this.quotaBuyState = promotion.getQuotaBuyState();
        this.givePointState = promotion.getGivePointState();
        this.quotaUserNum = promotion.getQuotaUserNum();
        this.beginTime = promotion.getBeginTime();
        this.endTime = promotion.getEndTime();
        this.creator = admins.get(promotion.getCreator());
        this.updator = admins.get(promotion.getUpdator());
        this.auditor = admins.get(promotion.getAuditor());
        this.promotionType = promotion.getPromotionType();
        this.ruleId = promotion.getRuleId();
        this.createTime = promotion.getCreateTime();
        this.updateTime = promotion.getUpdateTime();
    }
}
