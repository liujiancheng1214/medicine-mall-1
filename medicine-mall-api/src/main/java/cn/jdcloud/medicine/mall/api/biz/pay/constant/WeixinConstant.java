package cn.jdcloud.medicine.mall.api.biz.pay.constant;

import cn.jdcloud.framework.core.common.ConstantLoader;

import java.util.Map;

/**
 * Created by yanghuoyun on 2017/6/7.
 */
public class WeixinConstant implements ConstantLoader {



    //接口返回状态
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    //支付结果状态
    public static final String PAY_SUCCESS = "SUCCESS";//支付成功
    public static final String PAY_REFUND = "REFUND";//转入退款
    public static final String PAY_NOTPAY = "NOTPAY";//未支付
    public static final String PAY_CLOSED = "CLOSED";//已关闭
    public static final String PAY_REVOKED = "REVOKED";//已撤销（刷卡支付）
    public static final String PAY_USERPAYING = "USERPAYING";//用户支付中
    public static final String PAY_ERROR = "PAYERROR";//支付失败(其他原因，如银行返回失败)
    //自定义：用户取消
    public static final String PAY_CANCEL = "CANCEL";

    public static final String TRADE_TYPE_APP = "APP"; //app支付
    public static final String TRADE_TYPE_JS ="JSAPI"; //公众号支付
    public static final String TRADE_TYPE_NATIVE ="NATIVE"; //扫码支付

    public static final String PACKAGE = "Sign=WXPay";
    public static final String PRE_PAY_URI = "https://api.mch.weixin.qq.com/pay/unifiedorder";//创建订单
    public static final String QUERY_ORDER_URI = "https://api.mch.weixin.qq.com/pay/orderquery";//查询订单

    /***********************************参数配置区***********************************/

    /****************************************公众号**********************************************/
    public static String APP_ID = "wx0aa76c7553006588";
    public static String APP_SECRET = "493be4bc2c7a042559c27cf6cdd7a022";
    public static String PARTNER_ID = "1484269562";
    public static String PARTNER_KEY = "U06MIRBRVB3FdxrNsekfQXYX4hUxaNmA";

    //网页授权
    public static String OAUTH2_REDIRECT = "https://open.weixin.qq.com/connect/oauth2/authorize";
    //获取openId
    public static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String OFFICIAL_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public static String OFFICIAL_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    public static String GET_WEIXIN_USER_INFO  ="https://api.weixin.qq.com/sns/userinfo";

    public static String LOGIN_REDIRECT_URI;
    public static String NOTIFY_URL;

    @Override
    public void load(Map<String, String> map) {
        APP_ID = map.get("weixin.pay.app.id");
        APP_SECRET = map.get("weixin.pay.app.secret");
        PARTNER_ID = map.get("weixin.pay.partner.id");
        PARTNER_KEY = map.get("weixin.pay.partner.key");
        LOGIN_REDIRECT_URI = map.get("weixin.user.login.redirect");
        NOTIFY_URL = map.get("weixin.pay.notify.url");
    }
}
