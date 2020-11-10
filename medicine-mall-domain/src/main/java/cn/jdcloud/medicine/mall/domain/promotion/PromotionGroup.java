package cn.jdcloud.medicine.mall.domain.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author chenQF
 * @desc 团购模板对象
 * @date 2020/8/22 0022 14:18
 */
@Data
@TableName("t_promotion_group")
public class PromotionGroup {

    private Integer id; //团购模板id
    private String groupName; //团购模板名称
    private Integer groupType; //团购类型(1.老带新团;2:中团;3:阶梯团)
    private Integer onlyNew; //如果是老带新团，是否只允许邀请新客户（1:只允许新客户;2:也可邀请老见客户）'
    private Integer groupCondition; //成团条件（1:按参团人数;2:按成交数量）
    private Integer minSuccessNum; //成团数量(如果按照参团的人数,这里为最低参团的人数;如果按购买数量,这里为最低的购买数量)
    private Integer maxGroupNum; //最大开团数量(-1为不限制)
    private Integer leaderRebate; //团长返利,建议支付完成后，成团在返还
    private Integer groupStatus; //团购状态 1可用 2不可用
    private String rule; //活动规则
    private Date createTime;
    private Date updateTime;

}
