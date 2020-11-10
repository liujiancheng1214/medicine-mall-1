package cn.jdcloud.medicine.mall.api.biz.promotion.vo;

import java.util.Date;

import lombok.Data;


@Data
public class GroupInfoVo {

	    private Integer id;
	    private Integer promotionId; //活动规则id
	    private Integer plushUserId; //发起人id
	    private Integer userNum; //参团人数
	    private Integer itemNum; //购买数量
	    private Integer status; //状态(1拼团中 2拼团成功 3已取消 4活动结束未成功)
	    private Date createTime; //发起时间
	    private Date endTime; //结束时间
	    private Date updateTime; //更新时间
}
