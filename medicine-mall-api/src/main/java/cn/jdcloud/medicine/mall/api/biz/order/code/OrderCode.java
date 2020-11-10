package cn.jdcloud.medicine.mall.api.biz.order.code;

import cn.jdcloud.framework.core.common.ErrorCode;

/**
 * 异常代码类
 */
public enum OrderCode implements ErrorCode{

	/**
	 * 购物车相关 =================================
	 */

	ORDER_PARAM_ERROR(400,"订单参数错误"),

	ORDER_PRODUCT_EMPTY(402,"未选择商品"),

	ORDER_DATA_ERROR(500, "订单数据异常"),
	
	ORDER_COUPON_ERROR(501,"优惠券使用不合法");

	
	private static final String TYPE = "order";

	private int code;

	private String msg;

	OrderCode(int code, String msg) {
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
