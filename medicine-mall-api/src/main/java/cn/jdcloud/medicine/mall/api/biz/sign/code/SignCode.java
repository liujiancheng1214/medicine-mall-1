package cn.jdcloud.medicine.mall.api.biz.sign.code;

import cn.jdcloud.framework.core.common.ErrorCode;

public enum SignCode  implements ErrorCode{
      SIGN_STATUS_ERROR(1000,"今日已签到");

	private static final String TYPE = "sign";

	private int code;

	private String msg;

	SignCode(int code, String msg) {
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
