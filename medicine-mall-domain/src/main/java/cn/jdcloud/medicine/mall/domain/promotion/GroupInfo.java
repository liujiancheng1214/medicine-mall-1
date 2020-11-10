package cn.jdcloud.medicine.mall.domain.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author chenQF
 * @desc 拼团表
 * @date 2020/8/24 0024 13:44
 */
@Data
@TableName("t_group_info")
public class GroupInfo {
    private Integer id;
    private String itemNo;
    private Integer promotionId; //活动规则id
    private Integer plushUserId; //发起人id
    private Integer userNum; //参团人数
    private Integer itemNum; //购买数量
    private Integer status; //状态(0 待支付 1拼团中 2拼团成功 3已取消 4活动结束未成功)
    private Integer plushType; //发起类型 1后台发起 2前台发起
    private Date createTime; //发起时间
    private Date endTime; //结束时间
    private Date updateTime; //更新时间

}
