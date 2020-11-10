package cn.jdcloud.medicine.mall.api.biz.pay.service;

import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.NotifyReq;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReq;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReturn;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;

/**
 * @Description: 微信支付Service
 * @author: yanghuoyun
 * @since: 2017/06/08 14:20
 * @version: v1.0
 */
public interface WeixinPayService {

    /**
     * 调用统一下单api
     * @param ur
     * @return UnifiedOrderReturn
     */
    UnifiedOrderReturn unifiedOrder(UnifiedOrderReq ur);



    /**
     * 微信回调组装成orderpay
     * @param nr
     * @return OrderPay
     */
    OrderPay wrapOrderPay(NotifyReq nr);

    /**
     * 主动查询支付结果
     */
    OrderPay queryPayResult(Integer orderPayId, Byte clientType);

}