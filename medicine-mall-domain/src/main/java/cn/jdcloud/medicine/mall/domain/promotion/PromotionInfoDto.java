package cn.jdcloud.medicine.mall.domain.promotion;

import java.util.Date;

import lombok.Data;

@Data
public class PromotionInfoDto {
	private Integer minSuccessNum;
	private Integer userNum; //参团人数
	private Integer itemNum; //购买数量
	private byte groupCondition;
	private Date endTime;
	private byte status;
}
