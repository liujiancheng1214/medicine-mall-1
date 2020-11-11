package cn.jdcloud.medicine.mall.api.biz.user.enums;

import cn.jdcloud.framework.core.common.ErrorCode;

/**
 * 异常代码类
 */
@SuppressWarnings({"ALL", "AliMissingOverrideAnnotation"})
public enum UserCode implements ErrorCode{

	/**
	 * ============== 文件相关 =========================
	 * 800
	 */
	NOTFINDFILE_ERROR(800, "文件读取失败,系统找不到指定路径"),
	EXPORT_ERROR(801, "导出数据失败"),
	FILE_FORMAT_ERROR(803, "文件格式不正确"),
	READ_ERROR(802, "导入数据失败"),

	/**
	 * ============== 用户相关 =========================
	 * 900
	 */
	/**902身份证号码不合法**/
	ID_CARD_IS_WRONGFULNESS(902, "身份证号码格式不正确"),
	/**903手机验证码不合法**/
	SMS_CODE_IS_WRONGFULNESS(903, "手机验证码应为4位数字"),
	/**905身份证号码错误**/
	ID_CARD_ERROR(905, "身份证号码错误"),
	/**906新手机号码与原始手机号码相同**/
	MOBILE_OLD_NEW_NAME(906, "新手机号与原始手机号相同"),
	/**909操作无权限**/
	OPERATE_NO_PERMISSIONS(909, "操作无权限"),
	/** 910手机号不合法 **/
	MOBILE_IS_WRONGFULNESS(910, "手机号应为11位数字"),
	/** 911手机未注册 **/
	MOBILE_IS_NOT_EXISTS(911, "手机号未注册"),
	/** 912手机号已存在 **/
	MOBILE_IS_EXISTS(912, "手机号已注册"),
	// MOBILE_IS_EXISTS_LOGIN(913, ""),
	/** 914手机验证码错误 **/
	VERIFY_CODE_ERROR(914, "验证码不正确"),
	/** 915手机验证码失效 **/
	VERIFY_CODE_FAILED(915, "手机验证码失效"),
	/** 916密码不合法 **/
	PASSWORD_IS_WRONGFULNESS(916, "密码格式不正确"),
	/** 917密码错误 **/
	PASSWORD_IS_ERROR(917, "密码不正确"),
	/** 918手机验证码发送失败 **/
	SEND_SMSMESSAGE_FAIL(918, "手机验证码发送失败"),
	/** 原始密码错误 **/
	PASSWORD_OLD_IS_ERROR(920, "旧密码错误"),
	/** 原始密码与新密码相同 **/
	PASSWORD_OLD_NEW_SAME(921, "新密码和旧密码不能相同"),
	/**密码加密错误*/
	PASSWORD_ENCRYPT_ERROR(986, "密码加密错误"),
	/**991token不存在**/
	TOKEN_IS_NOT_EXISTS(991, "token不存在"),
	/**992token不合法**/
	TOKEN_IS_WRONGFULNESS(992, "token格式不正确"),
	/**993token已失效**/
	TOKEN_IS_INVALID(993,"登录已失效"),
	/**995用户不存在**/
	USER_IS_NOT_EXIST(995, "手机号未注册"),//用户不存在
	/**995用户不存在**/
	USER_BASE_NOT_EXIST(990, "用户不存在"),//用户不存在
	/** 997 **/
	LOGIN_IS_INVALID(997,"请先进行登录"),
	
	/** 998 获取验证码的次数已达上限 **/
	VERIFY_CODE_FAILED_COUNT_UPPER_LIMIT(998,"获取验证码的次数已达上限"),

	/** 999 联系电话应为十一位数字或者十二位数字 **/
	CONTACT_MOBILE_LENGTH_ERROR(999,"联系电话应为11位或12位数字"),

	USER_CHECK_MOBILE_NAME(1002,"用户名或手机号重复！"),

	THERE_ARE_CUSTOMERS_UNDER_THE_LEVEL(1003,"该等级下还存在客户！");

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
