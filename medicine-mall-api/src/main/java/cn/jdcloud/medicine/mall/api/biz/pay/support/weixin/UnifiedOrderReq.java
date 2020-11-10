package cn.jdcloud.medicine.mall.api.biz.pay.support.weixin;

import cn.jdcloud.framework.core.adapter.CDataAdapter;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.WeixinUtil;
import cn.jdcloud.medicine.mall.api.sys.constant.SysConstant;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import lombok.Data;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanghuoyun on 2017/6/6.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class UnifiedOrderReq {
    //应用ID
    @XmlElement(name="appid")
    private String appId;

    //商户号
    @XmlElement(name="mch_id")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String mchId;

    //随机字符串
    @XmlElement(name="nonce_str")
    private String nonceStr;

    //签名
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String sign;

    /**
     * 商品描述交易字段格式根据不同的应用场景按照以下格式：
     * APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     */
    private String body;

    //商户订单号
    @XmlElement(name="out_trade_no")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String outTradeNo;

    //总金额
    @XmlElement(name="total_fee")
    private Integer totalFee;

    //终端IP
    @XmlElement(name="spbill_create_ip")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String spBillCreateIp;

    //交易起始时间
    @XmlElement(name="time_start")
    private String timeStart;

    //交易结束时间
    @XmlElement(name="time_expire")
    private String timeExpire;

    //通知地址
    @XmlElement(name="notify_url")
    private String notifyUrl;

    //交易类型
    @XmlElement(name="trade_type")
    private String tradeType;

    //openId
    @XmlElement(name = "openid")
    private String openId;

    public UnifiedOrderReq(){}

    protected Logger log = Logger.getLogger(getClass());

    public UnifiedOrderReq(OrderPay op, String clientIp, Byte clientType){
        Map<String,String> params = new HashMap<>();

        this.appId = WeixinConstant.APP_ID;
        this.mchId = WeixinConstant.PARTNER_ID;
        this.notifyUrl = WeixinConstant.NOTIFY_URL;
        this.nonceStr = WeixinUtil.randomUUID();
        this.body = op.getSubject()+"-"+op.getBody();
        this.outTradeNo = op.getId().toString();
        if(SysConstant.IS_TEST){
            this.totalFee =1;
        }else{
            this.totalFee = WeixinUtil.covertAmount(op.getTradeAmount());
        }
        this.spBillCreateIp = clientIp;
        Date time = op.getCreateTime();
        this.timeStart = WeixinUtil.formatDate(time);
        this.timeExpire = WeixinUtil.getExpiredTime(time);
        switch (clientType){
            case OrderPay.CLIENT_TYPE_JS:
                this.tradeType = WeixinConstant.TRADE_TYPE_JS;
                this.openId = op.getOpenId();
                break;
             default:
                this.tradeType = WeixinConstant.TRADE_TYPE_NATIVE;
        }

        params.put("appid",appId);
        params.put("mch_id",mchId);
        params.put("nonce_str",nonceStr);
        params.put("body",body);
        params.put("out_trade_no",outTradeNo);
        params.put("total_fee",totalFee.toString());
        params.put("spbill_create_ip",spBillCreateIp);
        params.put("time_start",timeStart);
        params.put("time_expire",timeExpire);
        params.put("notify_url",notifyUrl);
        params.put("trade_type",tradeType);
        params.put("openid",openId);
        log.info("weixin gong zhong hao pay total_fee = " + totalFee);
        this.sign = WeixinUtil.Md5Sign(params, clientType);
    }
}
