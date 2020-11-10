package cn.jdcloud.medicine.mall.domain.promotion;

import java.util.Date;

import lombok.Data;

@Data
public class PromotionItemUserDto {
	private Integer userId;
	private String headImg;
	private String name;
	private Integer groupId;
	private Date createTime;
	private String mobile; 
}
