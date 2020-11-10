package cn.jdcloud.medicine.mall.api.biz.pay.vo.weixin;

import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReturn;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import lombok.Data;

/**
 * Created by yanghuoyun on 2017/6/6.
 */
@Data
public class OrderParamsWebVo {

    //订单ID
    private String orderId;

    private Integer orderPayId;
    //金额
    private String amount;
    //二维码地址
    private String codeUrl;
    //随机字符串 - 不长于32位
    private String nonceStr;
    //时间戳 - 单位为秒10位
    private String timestamp;

    public OrderParamsWebVo(UnifiedOrderReturn ur, OrderPay op){
        this.orderPayId = op.getId();
        this.orderId = op.getRelatedId();
        this.amount = op.getTradeAmount().toString();
        this.nonceStr = ur.getNonceStr();
        long time = System.currentTimeMillis();
        this.timestamp = String.valueOf(time/1000);
        this.codeUrl = ur.getCodeUrl();
    }
}
