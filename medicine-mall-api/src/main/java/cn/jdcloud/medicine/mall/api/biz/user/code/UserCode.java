package cn.jdcloud.medicine.mall.api.biz.user.code;

import cn.jdcloud.framework.core.common.ErrorCode;

public enum UserCode implements ErrorCode{

	
	

	MOBILE_EXIST_ERROR(400,"用户已存在"),
	
	MOBILE__NO_EXIST_ERROR(401,"用户不存在"),

	PASSWORD_PARAM_ERROR(500, "密码错误");

	private static final String TYPE = "user";

	private int code;

	private String msg;

	UserCode(int code, String msg) {
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
