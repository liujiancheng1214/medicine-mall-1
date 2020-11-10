package cn.jdcloud.medicine.mall.api.biz.pay.vo.alipay;

import lombok.Data;

/**
 * @Description: 支付宝支付 客户端同步验证-alipay_trade_app_pay_responseVO
 * @author: yuanfei
 * @since: 2017/4/9 10:50
 * @version: v1.0
 */
@Data
public class AlipayTradeAppPayResponseVO {
    private String code;            // 结果码
    private String msg;             // 处理结果的描述，信息来自于code返回结果的描述
    private String app_id;          // 支付宝分配给开发者的应用Id
    private String auth_app_id;
    private String out_trade_no;    // 商户网站唯一订单号
    private String trade_no;        // 该交易在支付宝系统中的交易流水号。最长64位
    private String total_amount;    // 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01,100000000.00]，精确到小数点后两位
    private String seller_id;       // 收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
    private String charset;         // 编码格式
    private String timestamp;       // 时间  格式 2016-10-11 17:43:36
}