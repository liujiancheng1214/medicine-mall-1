package cn.jdcloud.medicine.mall.api.biz.pay.vo.weixin;

import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReturn;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.WeixinUtil;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieqiujin on 2017/8/23.
 */
@Data
public class OrderParamsH5Vo {
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

    private Integer orderPayId;

    private String openId;

    private String signType;

    public OrderParamsH5Vo(){}

    public OrderParamsH5Vo(UnifiedOrderReturn ur, OrderPay op, Byte clientType){
        Map<String,String> map = new HashMap<>();
        this.appId = ur.getAppId();
        this.prePayId = ur.getPrepayId();
        this.partnerId = ur.getMchId();
        this.packAge = "prepay_id="+ur.getPrepayId()+"";
        this.nonceStr = WeixinUtil.randomUUID();
        long time = System.currentTimeMillis();
        this.timestamp = String.valueOf(time/1000);
        this.signType = WeixinUtil.SIGN_MD5;
        map.put("appId",appId);
        map.put("nonceStr",nonceStr);
        map.put("package",packAge);
        map.put("signType",signType);
        map.put("timeStamp",timestamp);
        this.sign = WeixinUtil.Md5Sign(map, clientType);
        this.orderPayId = op.getId();
        this.openId = op.getOpenId();
    }
}
