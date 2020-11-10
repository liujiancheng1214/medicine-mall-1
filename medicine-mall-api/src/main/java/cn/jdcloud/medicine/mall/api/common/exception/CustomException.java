package cn.jdcloud.medicine.mall.api.common.exception;


import cn.jdcloud.framework.core.common.ErrorCode;

/**
 * @author qun.xu
 * @desc 自定义异常
 * @date 2018/9/4 16:14
 */
public class CustomException extends RuntimeException implements ErrorCode {

	private static final long serialVersionUID = -123L;

	private int code;

	private String msg;

	private String errorType;


	public CustomException(ErrorCode code) {
		super(code.getMsg());
		this.code = code.getCode();
		this.msg = code.getMsg();
	}

	public CustomException(int code, String message) {
		super(message);
		this.code = code;
		this.msg = message;
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
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
		return msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getErrorType() {
		return null;
	}

	public void setErrorType(String errorType) {
		 this.errorType=errorType;
	}
}
