package cn.jdcloud.medicine.mall.api.biz.pay.support.weixin;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by yanghuoyun on 2017/6/6.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class QueryOrderReturn {
    //返回状态码 SUCCESS/FAIL
    @XmlElement(name="return_code")
    private String returnCode;

    //返回信息
    @XmlElement(name="return_msg")
    private String returnMsg;

    //应用ID
    @XmlElement(name="appid")
    private String appId;

    //商户号
    @XmlElement(name="mch_id")
    private String mchId;

    //随机字符串
    @XmlElement(name="nonce_str")
    private String nonceStr;

    //签名
    private String sign;

    //业务结果  SUCCESS/FAIL
    @XmlElement(name="result_code")
    private String resultCode;

    //错误代码
    @XmlElement(name="err_code")
    private String errCode;

    //错误代码描述
    @XmlElement(name="err_code_des")
    private String errCodeDes;


    //用户标识 用户在商户appid下的唯一标识
    @XmlElement(name="openid")
    private String openId;

    //交易类型
    @XmlElement(name="trade_type")
    private String tradeType;

    //交易状态
    //SUCCESS—支付成功  REFUND—转入退款  NOTPAY—未支付  CLOSED—已关闭
    //REVOKED—已撤销（刷卡支付）  USERPAYING--用户支付中  PAYERROR--支付失败(其他原因，如银行返回失败)
    @XmlElement(name="trade_state")
    private String tradeState;

    //交易状态描述
    @XmlElement(name="trade_state_desc")
    private String tradeStateDesc;

    //付款银行 银行类型见银行列表
    @XmlElement(name="bank_type")
    private String bankType;

    //订单总金额,单位为分
    @XmlElement(name="total_fee")
    private String totalFee;

    //货币种类,默认人民币：CNY
    @XmlElement(name="fee_type")
    private String feeType;

    //现金支付金额，详见支付金额
    @XmlElement(name="cash_fee")
    private String cashFee;

    //现金支付货币类型，默认人民币：CNY
    @XmlElement(name="cash_fee_type")
    private String cashFeeType;

    //微信支付订单号
    @XmlElement(name="transaction_id")
    private String transactionId;

    //商户订单号
    @XmlElement(name="out_trade_no")
    private String outTradeNo;

    //支付完成时间,格式为yyyyMMddHHmmss
    @XmlElement(name="time_end")
    private String timeEnd;

    //=====================可选参数，检验sign时需要========================
    //设备号 微信支付分配的终端设备号
    @XmlElement(name="device_info")
    private String deviceInfo;
    //是否关注公众账号
    @XmlElement(name="is_subscribe")
    private String isSubscribe;
    //代金券金额
    @XmlElement(name="coupon_fee")
    private String couponFee;
    //代金券使用数量
    @XmlElement(name="coupon_count")
    private String couponCount;
    //商家数据包
    @XmlElement(name="attach")
    private String attach;
    //    代金券ID	coupon_id_$n	否	String(20)	10000	代金券或立减优惠ID,$n为下标，从0开始编号
    //    单个代金券支付金额	coupon_fee_$n	否	Int	100	单个代金券或立减优惠支付金额,$n为下标，从0开始编号
    //=====================$n下标参数默认为null，不参与计算sign========================
}
