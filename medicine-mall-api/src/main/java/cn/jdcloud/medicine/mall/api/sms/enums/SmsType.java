package cn.jdcloud.medicine.mall.api.sms.enums;


import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;

/**
 * Created by qun.xu on 2017/3/31.
 */
public enum SmsType {

    //通用验证码
    REGISTER(1, SmsConstant.SMS_TEMPLATE_COMMON, SmsConstant.VALID_CODE),
    FORGOT_PWD(2,SmsConstant.SMS_TEMPLATE_COMMON, SmsConstant.VALID_CODE),
    EN_TEST_SIGN(99,SmsConstant.SMS_TEMPLATE_COMMON, SmsConstant.VALID_CODE);
//    GENERATE_ACCOUNT_PASSWORD_SMS_CODE(2, SmsConstant.GENERATE_ACCOUNT_PASSWORD_SMS_CODE, SmsConstant.TEMPLATE),
    //短信通知
//    TEMPLATE_SMS_BAIQU_BAICHANG(3,SmsConstant.BAIQU_BAICHANG_NOTICE,SmsConstant.TEMPLATE),

//    //忘记密码
//    FORGOT_PWD(2,SmsConstant.TEMP_FORGOT_PWD, SmsConstant.VALID_CODE),
//    //修改绑定手机号码
//    UPDATE_MOBILE(3,SmsConstant.TEMP_UPDATE_MOBILE, SmsConstant.VALID_CODE),
//    //快捷登录
//    USER_LOGIN(4,SmsConstant.TEMP_LOGIN, SmsConstant.VALID_CODE),

    //------------------------------------ THIRD  PARTY  第三方应用  ----------------------------------------
    //1.卢恩英语测试报名
//    EN_TEST_SIGN(99,SmsConstant.TEMPLATE_EN_TEST,SmsConstant.TEMPLATE);




    private int value;
    //对应的短信模板
    private String template;
    private byte type;

    SmsType(int value, String template, byte type) {
        this.value = value;
        this.template = template;
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public static SmsType get(int value){
        for (SmsType st : values()) {
            if (value == st.getValue()) {
                return st;
            }
        }
        return null;
    }
}
