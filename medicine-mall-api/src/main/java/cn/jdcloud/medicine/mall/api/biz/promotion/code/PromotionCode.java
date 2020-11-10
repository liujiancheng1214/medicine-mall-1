package cn.jdcloud.medicine.mall.api.biz.promotion.code;

import cn.jdcloud.framework.core.common.ErrorCode;

public enum PromotionCode  implements ErrorCode{

	PROMOTION_STATUS_ERROR(100,"活动状态无效"),
	
	PROMOTION_TEMPLATE_STATUS_ERROR(101,"活动模板状态无效"),
	
	PROMOTION_USER_NUM_OVERFLOW(102,"活动人数已满"),
	
	PROMOTION_ITEM_NUM_OVERFLOW(103,"成交量已满"),
	PROMOTION_FINISHED(101,"活动已结束")
	
	;
	
	

	private static final String TYPE = "promotion";

	private int code;

	private String msg;

	PromotionCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getErrorType(){
		return TYPE;
	}
}
