package cn.jdcloud.medicine.mall.api.biz.admin.code;



import cn.jdcloud.framework.core.common.ErrorCode;

public enum AdminCode implements ErrorCode {

    //管理员维护相关错误代码
    ADMIN_IS_NOT_EXIST(990, "用户不存在"),
	//管理员维护相关错误代码
	ADMIN_IS_EXIST(980, "用户已存在"),
    /**
     * 927用户名或者密码不正确
     **/
    PASSWORD_OR_account_WRONG(927, "用户名或者密码不正确!"),
    LOGIN_IS_INVALID(997, "登录已失效，请重新登录"),
	/**
	 * 917密码错误
	 **/
	PASSWORD_IS_ERROR(917, "密码错误"),
	/**
	 * 918手机验证码发送失败
	 **/
	SEND_SMSMESSAGE_FAIL(918, "手机验证码发送失败"),
	/**
	 * 原始密码错误
	 **/
	PASSWORD_OLD_IS_ERROR(920, "旧密码错误"),
	/**
	 * 原始密码与新密码相同
	 **/
	PASSWORD_OLD_NEW_SAME(921, "新密码和旧密码不能相同"),
	/**
	 * 923两次密码不一致
	 **/
	NEWPASSWORD_ISNOT_SAME(923, "输入的两次密码不一致"),

	UN_AUTH(4401, "用户未登陆,请登录!"),
	SESSION_TIMEOUT(4433, "超时退出登录"),
	UN_AUTHZ(4403, "用户没有访问权限"),
	/**
	 * 925账户为空
	 **/
	ACCOUNT_IS_EMPTY(925, "账号为空!"),
	/**
	 * 926密码为空
	 **/
	PASSWORD_IS_EMPTY(926, "密码为空!"),

	/**
	 * 账号被锁定
	 **/
	ACCOUNT_IS_LOCK(928, "账号被锁定"),

	/**
	 * 密码加密错误
	 */
	PASSWORD_ENCRYPT_ERROR(986, "密码加密错误"),

	/**
	 * 993token已失效
	 **/
	TOKEN_IS_INVALID(993, "登录已失效");



	private int code;
	private String msg;

	AdminCode(int code, String msg) {
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
	public static AdminCode get(int code) {
		for (AdminCode item : values()) {
			if (code == item.getCode()) {
				return item;
			}
		}
		return null;
	}
}
