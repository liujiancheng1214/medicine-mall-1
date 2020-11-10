package cn.jdcloud.medicine.mall.api.biz.pay.constant;

import cn.jdcloud.framework.core.common.ConstantLoader;

import java.util.Map;

/**
 * @Description: 支付宝App支付常量类
 * @version: v1.0
 */
public class AlipayConstant implements ConstantLoader{

    /** ===================== 支付宝异步校验状态 start ======================**/
    public static String ALI_PAY_STATUS_TRADE_FINISHED;                             // 交易结束，不可退款
    public static String ALI_PAY_STATUS_TRADE_SUCCESS;                              // 交易支付成功
    public static String ALI_PAY_STATUS_WAIT_BUYER_PAY;                             // 交易创建，等待买家付款
    public static String ALI_PAY_STATUS_TRADE_CLOSED;                               // 未付款交易超时关闭，或支付完成后全额退款
    /** ===================== 支付宝异步校验状态 end ======================**/
    /** ===================== 支付宝创建订单支付信息 start ======================**/

    public static final String SIGN_TYPE = "RSA2";                                       // 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    public static final String CHARSET = "UTF-8";                                        // 请求和签名使用的字符编码格式，支持GBK和UTF-8
    public static final String QUICK_MSECURITY_PAY = "QUICK_MSECURITY_PAY";              // 销售产品码，是固定值
    public static final String TIMEOUT_EXPRESS = "30m";                                  // 超时关闭时间

    public static String SERVER_URL = "https://openapi.alipay.com/gateway.do";          // 支付宝网关（固定）
    public static String OWNER_APP_ID;                                                  // APP_ID即创建应用后生成
    public static String OWNER_APP_PRIVATE_KEY;                                         // 商户应用私钥
    public static String OWNER_ALIPAY_PUBLIC_KEY;                                       // 支付宝公钥，由支付宝生成
    public static String OWNER_SELLER_ID;                                               // seller_id, 以2088开头的纯16位数字


    public static String DRIVER_APP_ID;
    public static String DRIVER_APP_PRIVATE_KEY;
    public static String DRIVER_ALIPAY_PUBLIC_KEY;
    public static String DRIVER_SELLER_ID;

    public static String NOTIFY_URL;                                                    // 异步通知地址，需要http完整路径，不允许加参数如?id=123


    /** ===================== 支付宝创建订单支付信息 end ======================**/


    @Override
    public void load(Map<String,String> map) {
//        SERVER_URL = map.get("alipay.order.server.url");
//        OWNER_APP_ID = map.get("alipay.order.owner.appid");
//        OWNER_APP_PRIVATE_KEY = map.get("alipay.order.owner.app.private.key");
//        OWNER_ALIPAY_PUBLIC_KEY = map.get("alipay.order.owner.alipay.public.key");
//        OWNER_SELLER_ID = map.get("alipay.order.owner.seller.id");
//        NOTIFY_URL = map.get("alipay.order.notify.url");
    }
}