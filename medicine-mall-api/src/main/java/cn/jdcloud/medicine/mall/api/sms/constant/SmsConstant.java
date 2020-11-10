package cn.jdcloud.medicine.mall.api.sms.constant;

import cn.jdcloud.framework.core.common.ConstantLoader;

import java.util.Map;

/**
 * Created by qun.xu
 * on 2017/4/11.
 * 手机短信常量
 */
public class SmsConstant implements ConstantLoader {

    /** =====================固定区======================**/
    //发送成功
    public static final int DY_SUCCESS = 1;
    //发送失败
    public static final int DY_FAULT =2;
    //手机号码格式错误
    public static final int DY_MOBILE_ERROR = 3;
    //出发业务留空
    public static final int DY_SURPASS_LIMIT = 4;

    public static final byte VALID_CODE = 1;

    public static final byte TEMPLATE = 2;

    //产品名称:云通信短信API产品,开发者无需替换
    public static  String  PRODUCT = "Dysmsapi";

    public static  String  ALIYUN_URL = "dysmsapi.aliyuncs.com";

    /** =====================配置区======================**/
    //短信验证码长度
    public static  int CODE_LEN = 4;
    //每天最多发送短信条数
    public static  int BIG_COUNT = 10;
    //短信是否测试
    public static  boolean IS_TEST = false;
    public static  String  ALIYUN_KEY ;
    public static  String  ALIYUN_SECRET ;

    //------------------------------------ 短信模板  ----------------------------------------
//    public static String TEMPLATE_EN_TEST = new String();

    public static String DEFAULT_SMS_TITLE = "sms.title";

    public static String SMS_TEMPLATE_COMMON = "SMS_TEMPLATE_COMMON";

//    public static String GENERATE_ACCOUNT_PASSWORD_SMS_CODE;


    @Override
    public void load(Map<String,String> map) {
        IS_TEST =  Boolean.parseBoolean(map.get("sms.isTest"));
        ALIYUN_KEY = map.get("sms.aliyun.app.key");
        ALIYUN_SECRET = map.get("sms.aliyun.app.secret");
        DEFAULT_SMS_TITLE  = map.get("sms.title");
        //短信模板
        SMS_TEMPLATE_COMMON = map.get("sms.validcode.template");
        //账户密码
//        GENERATE_ACCOUNT_PASSWORD_SMS_CODE = map.get("sms.template.account.password");

    }
}
