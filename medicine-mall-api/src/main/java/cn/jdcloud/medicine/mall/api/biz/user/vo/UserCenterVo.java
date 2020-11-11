package cn.jdcloud.medicine.mall.api.biz.user.vo;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class UserCenterVo {
	private String headImage;
	private String name;
	private String mobile;
	private String address;
	private int  couponNum;
	private BigDecimal fanyon;
	private BigDecimal balance;
	private int integral;
	
}
