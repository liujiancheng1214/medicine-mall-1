package cn.jdcloud.medicine.mall.api.biz.pay.beans;

import cn.jdcloud.framework.core.common.ErrorCode;

/**
 * 异常代码类
 */
public enum PayCode implements ErrorCode{

	/**
	 * 订单相关 =================================
	 * 1000
	 */
	/**1150订单不存在**/
	ORDER_IS_NOT_EXIST(1150,"订单不存在"),
	/**1151订单类型不存在**/
	ORDER_TYPE_IS_NOT_EXIST(1151,"订单类型不存在"),
	/**1152订单不允许取消**/
	ORDER_CAN_NOT_CANCEL(1152, "订单不允许取消"),
	/**1153订单不允许删除**/
	ORDER_CAN_NOT_DELETE(1153, "订单不允许删除"),
	/** 1154 请认证后创建订单**/
	OBJECT_NOT_EXIST(1154,"商品不存在"),

	/** 请勿重复支付 **/
	REPETITION_PAY(1157,"请勿重复支付"),
	/**1158您不能支付此订单**/
	ORDER_CAN_NOT_PAY(1158, "您不能支付此订单"),

	ORDER_UN_DONE(1162,"您有未完成的订单，请先完成订单"),

	ORDER_HAS_CANCEL(1163,"订单已取消"),

	ORDER_CANNOT_CANCEL(1164,"该订单不能取消"),


	/******* 支付宝支付异常 *******/
	ALI_PAY_CHECK_SIGN_ERROR(2000, "参数验证签名失败"),
	ALI_PAY_CREATE_ORDER_ERROR(2001, "创建订单信息异常"),
	ALI_PAY_CLIENT_NOTIFY_ERROR(2002, "马力客户端同步异常"),
	ALI_PAY_SERVER_NOTIFY_ERROR(2003, "支付宝服务端异步异常"),
	ALI_PAY_SERVER_QUERY_ERROR(2004, "支付宝服务端查询异常");

	private static final String TYPE = "pay";

	private int code;

	private String msg;

	PayCode(int code, String msg) {
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
