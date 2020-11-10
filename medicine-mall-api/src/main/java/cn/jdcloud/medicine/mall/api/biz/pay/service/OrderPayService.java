package cn.jdcloud.medicine.mall.api.biz.pay.service;

import cn.jdcloud.framework.common.enums.PayWay;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;

/**
 * @Description: 支付Service
 * @author: yanghuoyun
 * @since: 2017/06/07 14:20
 * @version: v1.0
 */
public interface OrderPayService {

    /**
     * 创建支付订单
     * @param extra
     * @return
     */
    OrderPay createPrepayOrder(String relatedId, byte payType, String extra, PayWay payWay, Integer userId, Byte clientType) throws ApiException;

    /**
     * 根据OrderId查询订单信息
     * @param orderPayId   内部订单ID
     * @return
     */
    OrderPay getOrderPayById(Integer orderPayId);

    /**
     * 根据OrderId查询订单信息
     * @return
     */
    OrderPay getByRelatedId(String relatedId, byte payType) throws ApiException;

    /**
     * 根据OrderId查询已经支付成功了的订单信息
     * @param orderId   内部订单ID
     * @return
     */
    OrderPay getSuccessOrderPay(String orderId, byte payType) throws ApiException;

    /**
     * 处理回调业务
     * 更新订单，抵用券，订单支付数据
     * @param orderPay   内部订单ID
     * @return
     */
    void dealNotifyOrderPay(OrderPay orderPay) throws ApiException;


}