package cn.jdcloud.medicine.mall.domain.integral;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data

@TableName("t_integral")
public class Integral {
	
	//签到获取积分
	public static final String INTEGRAL_TYPE_SIGN="1";
	//签到获取额外积分
	public static final String INTEGRAL_TYPE_SIGN_ADDITIONAL="2";
	// 购买商品获取积分
	public static final String INTEGRAL_TYPE_BUY_ITEM="3";
	// 兑换优惠券获取积分
	public static final String INTEGRAL_TYPE_COUPON="4";
	
	public static final Integer CHANGE_TYPE_OBTAIN=1;

	public static final Integer CHANGE_TYPE_USE=0;
	
	
	    private Integer id;

	    private Integer amount;

	    private String reamrk;

	    private String type;

	    private Integer userId;
	   
	    private Date createTime;

	    private String typeDesc;

	    private Integer changeType;
}
