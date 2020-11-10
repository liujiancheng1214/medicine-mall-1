package cn.jdcloud.medicine.mall.api.biz.pay.service.impl;

import cn.jdcloud.framework.common.enums.PayWay;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.pay.beans.PayCode;
import cn.jdcloud.medicine.mall.api.biz.pay.service.OrderPayService;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.sys.constant.AppConstant;
import cn.jdcloud.medicine.mall.dao.order.OrderMapper;
import cn.jdcloud.medicine.mall.dao.order.OrderPayMapper;
import cn.jdcloud.medicine.mall.dao.order.RefundOrderMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import cn.jdcloud.medicine.mall.domain.user.User;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 支付宝支付Service实现类
 * @author: yuanfei
 * @since: 2017/4/15 14:20
 * @version: v1.0
 */
@Service("orderPayService")
public class OrderPayServiceImpl implements OrderPayService {

    protected Logger log = Logger.getLogger(getClass());

    private final static BigDecimal HUNDRED = new BigDecimal(100);

    @Resource
    private OrderPayMapper orderPayMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RefundOrderMapper refundOrderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource(name="gson")
    private Gson gson;

    @Override
    @Transactional
    public OrderPay createPrepayOrder(String relatedId, byte payType, String extra, PayWay payWay, Integer userId, Byte clientType)throws
            ApiException {
        //根据OrderId查询是否有已经支付成功的订单
        OrderPay op = orderPayMapper.getSuccessOrderPay(relatedId,payType);
        if (op != null) {
            throw new ApiException(PayCode.REPETITION_PAY);
        }
        OrderPay orderPay = new OrderPay();
        String body = "";
        Date now = new Date();
        switch (payType) {
            case OrderPay.PAY_TYPE_PRODUCT:
                //根据订单ID查询订单信息
                Order order = orderMapper.selectById(relatedId);
                if (order == null) {
                    throw new ApiException(PayCode.ORDER_IS_NOT_EXIST);
                }
                if (order.getUserId() == null) {
                    throw new ApiException(UserCode.USER_IS_NOT_EXIST);
                }

                //如果用户ID和订单中的用户ID、订单状态不是待支付、订单中的金额小于0
                if (!userId.equals(order.getUserId()) ||
                        Order.STATUS_DFK != order.getOrderStatus() ||
                        order.getTotalAmount().compareTo(BigDecimal.ZERO) == -1) {
                    throw new ApiException(PayCode.ORDER_CAN_NOT_PAY);
                }
                orderPay.setTradeAmount(order.getTotalAmount());
                break;
            case OrderPay.PAY_TYPE_VIP:
//                VipOrder vipOrder = vipOrderMapper.selectById(relatedId);
//                if (vipOrder == null) throw new ApiException(PayCode.ORDER_IS_NOT_EXIST);
//                if (vipOrder.getUserId() == null) throw new ApiException(UserCode.USER_IS_NOT_EXIST);
//
//                //如果用户ID和订单中的用户ID、订单状态不是待支付、订单中的金额小于0
//                if (!userId.equals(vipOrder.getUserId()) ||
//                        VipOrder.STATUS_DFK != vipOrder.getOrderStatus() ||
//                        vipOrder.getTotalAmount().compareTo(BigDecimal.ZERO) == -1) {
//                    throw new ApiException(PayCode.ORDER_CAN_NOT_PAY);
//                }
//                orderPay.setTradeAmount(vipOrder.getTotalAmount());
                break;
            case OrderPay.PAY_TYPE_REFUND:
                //退款
//                RefundOrder vipOrder = refundOrderMapper.selectById(relatedId);
//                if (vipOrder == null) {
//                    throw new ApiException(PayCode.ORDER_IS_NOT_EXIST);
//                }
//                if (vipOrder.getUserId() == null) {
//                    throw new ApiException(UserCode.USER_BASE_NOT_EXIST);
//                }
//
//                //如果用户ID和订单中的用户ID、订单状态不是待支付、订单中的金额小于0
//                if (!userId.equals(vipOrder.getUserId()) ||
//                        VipOrder.STATUS_DFK != vipOrder.getOrderStatus() ||
//                        vipOrder.getTotalAmount().compareTo(BigDecimal.ZERO) == -1) {
//                    throw new ApiException(PayCode.ORDER_CAN_NOT_PAY);
//                }
//                orderPay.setTradeAmount(vipOrder.getTotalAmount());
                break;
            default:
        }
        //公众号支付
        if(clientType==OrderPay.CLIENT_TYPE_JS){
            User user = userMapper.selectById(userId);
            orderPay.setOpenId(user.getOpenid());
        }
        orderPay.setBody(body);
        orderPay.setSubject(AppConstant.APP_NAME);
        // 新增一笔支付订单数据
        orderPay.setPayWay(payWay);
        orderPay.setPayStatus(OrderPay.ORDER_PAY_ZFZ);
        orderPay.setRelatedId(relatedId);
        orderPay.setPayType(payType);
        if(extra!=null){
            orderPay.setExtraParams(extra.replace("\\",""));
        }
        orderPay.setTimeOut(new Date(now.getTime() + 30 * 60 * 1000));
        orderPay.setCreateTime(now);
        orderPay.setClientType(clientType);
        orderPayMapper.saveOrderPay(orderPay);
        return orderPay;
    }

    private String wrapBody(String productName){

        StringBuilder sb = new StringBuilder();
        sb.append(productName);
        return sb.toString();
    }

    /**
     * 根据orderId查询已经支付成功了的订单信息
     * @param orderId   内部订单ID
     * @return
     */
    @Override
    public OrderPay getSuccessOrderPay(String orderId,byte payType) {
        return orderPayMapper.getSuccessOrderPay(orderId,payType);
    }

    /**
     * 根据orderId查询已经支付成功了的订单信息
     * @param orderPayId   内部订单ID
     * @return
     */
    @Override
    public OrderPay getOrderPayById(Integer orderPayId) {
        return orderPayMapper.getOrderPayById(orderPayId);
    }

    @Override
    public OrderPay getByRelatedId(String relatedId, byte payType) throws ApiException{
        return orderPayMapper.getByRelatedId(relatedId,payType);
    }

    /**
     * 处理回调业务
     * 更新订单，抵用券，订单支付数据
     * 加入同步锁机制，防止并发调用
     * @param orderPayDto   内部订单ID
     * @return
     */
    @Override
    @Transactional
    public synchronized void dealNotifyOrderPay(OrderPay orderPayDto) throws ApiException {

        if(orderPayDto==null){
            log.info("deal orderPay params is null");
            return;
        }
        // 查询订单，判断是否已经回调处理，启用锁机制
        OrderPay op = orderPayMapper.getOrderPayById(orderPayDto.getId());
        if(op==null){
            log.error("orderPay notify enums:can not find old orderPay by "+gson.toJson(op));
            return;
        }
        //数据已经更新过
        if(op.getPayStatus()>OrderPay.ORDER_PAY_ZFZ){
            log.info("orderPay notify has been dealed oldDate="+gson.toJson(op)+" newDate="+gson.toJson(op));
            return;
        }
        //更新订单支付信息
        orderPayMapper.updateNotifyOrderPay(orderPayDto);

        switch (orderPayDto.getPayStatus()){
            case OrderPay.ORDER_PAY_ZFZ:
            case OrderPay.ORDER_PAY_JYGB:
            default:
                return;
            //如果支付成功，3-4都是成功
            case OrderPay.ORDER_PAY_JYCG:
            case OrderPay.ORDER_PAY_JYJS:
                break;
        }

        switch (orderPayDto.getPayType()) {
            case OrderPay.PAY_TYPE_PRODUCT:
                productPaySuccess(orderPayDto);
                break;
            case OrderPay.PAY_TYPE_VIP:
                vipOrderPaySuccess(orderPayDto);
                break;
            default:break;
        }
    }

    private void vipOrderPaySuccess(OrderPay op) {
        String orderId = op.getRelatedId();
        //判断一系列的条件
        if(null == orderId){
            log.error("orderPay data enums: op do not have vipOrderId :"+gson.toJson(op));
            return;
        }

    }

    /**
     * 订单支付成功后的一系列的处理
     * @param op
     */
    private void productPaySuccess (OrderPay op) {
        String orderId = op.getRelatedId();
        //判断一系列的条件
        if(null == orderId){
            log.error("orderPay data enums: op do not have orderId :"+gson.toJson(op));
            return;
        }
        Order order = orderMapper.selectById(orderId);
        Order orderUpdate = new Order();
        orderUpdate.setOrderId(orderId);
        //如果是普通订单
        if (order.getOrderType()==0){
            orderUpdate.setOrderStatus(Order.STATUS_YFK_DCL);
        }
        //如果是 拼团单
        if (order.getOrderType()==1){
            //todo: 如果支付 失败  删除 拼团成员表数据。  如果支付成功  结束该团实例。
        }
//        orderUpdate.setPayAmount(op.getPayAmount());
        //更新订单状态
        orderMapper.updateById(orderUpdate);

    }

}
