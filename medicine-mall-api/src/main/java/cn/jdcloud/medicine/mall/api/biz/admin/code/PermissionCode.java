package cn.jdcloud.medicine.mall.api.biz.admin.code;

import cn.jdcloud.framework.core.common.ErrorCode;

public enum PermissionCode implements ErrorCode {

    //权限值已存在
    PERM_VALUE_EXIST(800, "权限值已存在");

	private int code;
	private String msg;

	PermissionCode(int code, String msg) {
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
	public static PermissionCode get(int code) {
		for (PermissionCode item : values()) {
			if (code == item.getCode()) {
				return item;
			}
		}
		return null;
	}
}
