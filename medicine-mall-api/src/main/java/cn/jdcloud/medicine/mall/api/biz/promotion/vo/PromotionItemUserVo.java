package cn.jdcloud.medicine.mall.api.biz.promotion.vo;

import lombok.Data;

@Data
public class PromotionItemUserVo {
	private Integer userId;
	private String headImg;
	private String name;
	private Integer groupId;
	private String mobile;
	private String beforeTime;
}
