package cn.jdcloud.medicine.mall.domain.promotion;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_promotion_info")
public class PromotionInfo extends BaseDomain {
    @TableId(value = "promotion_id",type = IdType.AUTO)
    private Integer promotionId;
    /**模板Id*/
    @TableField(exist = false)
    private Integer promotionGroupId;
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
    /**活动状态  0 失效 1 有效*/
    private Byte status;
    /**是否给与积分(1:给予；2不给于)*/
    private Byte givePointState;
    /**是否限购(1:限购;2不限购)*/
    private Byte quotaBuyState;
    /**每人配额(几份)*/
    private Integer quotaUserNum;
    /**活动开始时间*/
    private Date beginTime;
    /**活动结束时间*/
    private Date endTime;
    /**创建人Id*/
    private Integer creator;
    /**更新人Id*/
    private Integer updator;
    /**审核员Id*/
    private Integer auditor;
    /**促销类型 (1:团购)*/
    private Byte promotionType;
    /**目标类型的规则Id(团购、买赠、打折）*/
    private Integer ruleId;

}
