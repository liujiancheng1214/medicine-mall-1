package cn.jdcloud.medicine.mall.api.biz.pay.support.weixin;

import cn.jdcloud.framework.utils.XmlUtil;
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
public class UnifiedOrderReturn {
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

    //预支付交易会话标识
    @XmlElement(name="prepay_id")
    private String prepayId;

    //交易类型
    @XmlElement(name="trade_type")
    private String tradeType;

    //二维码地址
    @XmlElement(name="code_url")
    private String codeUrl;

    public static void main(String[] args) {
        String xml = "";
        UnifiedOrderReturn uoReturn = XmlUtil.parseObject(xml,UnifiedOrderReturn.class);
        System.out.println(uoReturn.getReturnMsg());
    }
}
