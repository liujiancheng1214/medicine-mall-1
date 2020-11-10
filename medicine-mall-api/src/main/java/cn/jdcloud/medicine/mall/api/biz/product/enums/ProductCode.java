package cn.jdcloud.medicine.mall.api.biz.product.enums;

import cn.jdcloud.framework.core.common.ErrorCode;

/**
 * 异常代码类
 */
public enum ProductCode implements ErrorCode{

	/**
	 *  =================================
	 *  商品相关错误代码
	 *  =================================
	 */
	/**1150订单不存在**/
	PRODUCT_IS_NOT_EXIST(404,"商品不存在"),

	PRODUCT_OFF_SELL(405,"商品已下架"),

	PRODUCT_AUTH_ERROR(401, "支付宝服务端查询异常");

	private static final String TYPE = "product";

	private int code;

	private String msg;

	ProductCode(int code, String msg) {
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
