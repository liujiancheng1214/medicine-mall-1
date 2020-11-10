package cn.jdcloud.medicine.mall.api.biz.admin.code;


import cn.jdcloud.framework.core.common.ErrorCode;

public enum RoleCode implements ErrorCode {

    //权限值已存在
    ROLE_NAME_EXIST(850, "角色名已存在");

	private int code;
	private String msg;

	RoleCode(int code, String msg) {
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
	public static RoleCode get(int code) {
		for (RoleCode item : values()) {
			if (code == item.getCode()) {
				return item;
			}
		}
		return null;
	}
}
