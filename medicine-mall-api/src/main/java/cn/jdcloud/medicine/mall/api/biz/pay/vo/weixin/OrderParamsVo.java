package cn.jdcloud.medicine.mall.api.biz.pay.vo.weixin;

import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReturn;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.WeixinUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanghuoyun on 2017/6/6.
 */
@Data
public class OrderParamsVo {

    //应用ID
    private String appId;
    //商户号
    private String partnerId;
    //预支付交易会话ID - 微信返回的支付交易会话ID
    private String prePayId;
    //扩展字段 - 暂填写固定值Sign=WXPay
    private String packAge;
    //随机字符串 - 不长于32位
    private String nonceStr;
    //时间戳 - 单位为秒10位
    private String timestamp;
    //签名
    private String sign;

    private Long orderPayId;

    public OrderParamsVo(){}

    public OrderParamsVo(UnifiedOrderReturn ur, Byte from, Byte clientType){
        this.appId = ur.getAppId();
        this.partnerId = ur.getMchId();
        this.prePayId = ur.getPrepayId();
        this.packAge = WeixinConstant.PACKAGE;
        this.nonceStr = WeixinUtil.randomUUID();
        long time = System.currentTimeMillis();
        this.timestamp = String.valueOf(time/1000);
        Map<String,String> map = new HashMap<>();
        map.put("appid",appId);
        map.put("partnerid",partnerId);
        map.put("prepayid",prePayId);
        map.put("package",packAge);
        map.put("noncestr",nonceStr);
        map.put("timestamp",timestamp);
        this.sign = WeixinUtil.Md5Sign(map, clientType);
    }
}
