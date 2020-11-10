package cn.jdcloud.medicine.mall.client.code;

import cn.jdcloud.framework.core.common.ErrorCode;

/**
 */
public enum AuthCode implements ErrorCode {
    /**
     * 需要登录
     **/
    USER_NEEDLOGIN(901, "用户未登录"),
    /**
     * 无权限
     **/
    USER_PERMISSIONDENTAIL(405, "用户无权限"),

    APP_ACC_TOKEN_EXPRIE(444,"token已过期"),

    APP_RFF_TOKEN_EXPRIE(445,"登录已失效");

    private static final String TYPE = "auth";

    private int code;

    private String msg;


    AuthCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorType() {
        return TYPE;
    }
}
