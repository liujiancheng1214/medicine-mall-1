package cn.jdcloud.medicine.mall.api.biz.pay.support.weixin;

import cn.jdcloud.framework.core.adapter.CDataAdapter;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.WeixinUtil;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanghuoyun on 2017/6/6.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class QueryOrderReq {
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

    //商户订单号
    @XmlElement(name="out_trade_no")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String outTradeNo;

    //签名
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String sign;


    public QueryOrderReq(){}

    public QueryOrderReq(Integer orderPayId, Byte clientType){
        this.appId = WeixinConstant.APP_ID;
        this.mchId = WeixinConstant.PARTNER_ID;
        this.nonceStr = WeixinUtil.randomUUID();
        this.outTradeNo = orderPayId.toString();
        Map<String,String> params = new HashMap<>();
        params.put("appid",appId);
        params.put("mch_id",mchId);
        params.put("nonce_str",nonceStr);
        params.put("out_trade_no",outTradeNo);
        this.sign = WeixinUtil.Md5Sign(params, clientType);
    }
}
