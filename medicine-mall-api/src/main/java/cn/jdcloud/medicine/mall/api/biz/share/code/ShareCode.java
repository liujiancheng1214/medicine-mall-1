package cn.jdcloud.medicine.mall.api.biz.share.code;

import cn.jdcloud.framework.core.common.ErrorCode;

public enum ShareCode implements ErrorCode {

	SHARE_GROUP_EXIST(992, "您已经领取过了"),
	SPONSOR_ERROR(993, "您不能领取自己发起的分享");

	

	private int code;
	private String msg;

	ShareCode(int code, String msg) {
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
	public String getErrorType() {
		return "";
	}

	// 获得 enums 对象
	public static ShareCode get(int code) {
		for (ShareCode item : values()) {
			if (code == item.getCode()) {
				return item;
			}
		}
		return null;
	}
}
	
	
