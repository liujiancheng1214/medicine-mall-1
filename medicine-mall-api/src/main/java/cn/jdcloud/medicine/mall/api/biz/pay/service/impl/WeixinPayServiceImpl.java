package cn.jdcloud.medicine.mall.api.biz.pay.service.impl;

import cn.jdcloud.framework.common.enums.PayWay;
import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.framework.utils.XmlUtil;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.service.WeixinPayService;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.*;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.WeixinUtil;
import cn.jdcloud.medicine.mall.dao.order.OrderPayMapper;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 微信支付Service实现类
 * @author: huoyun.yang
 * @since: 2017/4/15 14:20
 * @version: v1.0
 */
@Service("weixinPayService")
public class WeixinPayServiceImpl implements WeixinPayService {

    protected Logger log = Logger.getLogger(getClass());

    @Resource
    private OrderPayMapper orderPayDao;
    /**
     * 调用统一下单api
     * @param ur
     * @return UnifiedOrderReturn
     */
    @Override
    public UnifiedOrderReturn unifiedOrder(UnifiedOrderReq ur) {
        String uri = WeixinConstant.PRE_PAY_URI;
        String xml = WeixinUtil.postRequest(uri,ur);
        if(xml==null){
            return null;
        }
        UnifiedOrderReturn uoReturn = XmlUtil.parseObject(xml,UnifiedOrderReturn.class);
        return uoReturn;
    }

    @Override
    public OrderPay wrapOrderPay(NotifyReq nr) {
        OrderPay op = new OrderPay();
        if(!StringUtils.isNumeric(nr.getOutTradeNo())){
            log.info("weixin notify return outTradeNo enums:"+nr.getOutTradeNo());
            return null;
        }
        op.setId(Integer.parseInt(nr.getOutTradeNo()));
        op.setPayWay(PayWay.weixin);
        op.setCallbackTime(new Date());
        op.setSuccTime(DateUtils.parseDate(nr.getTimeEnd(),WeixinUtil.DATE_FORMAT));
        op.setPayAmount(new BigDecimal(nr.getTotalFee()).divide(WeixinUtil.HUNDRUD));
        op.setPayCode(WeixinConstant.PAY_SUCCESS);
        op.setPayStatus(OrderPay.ORDER_PAY_JYCG);
        op.setTradeNo(nr.getTransactionId());
        return op;
    }

    /**
     * 主动查询支付结果
     */
    @Override
    public OrderPay queryPayResult(Integer orderPayId, Byte clientType) {
        QueryOrderReq qor = new QueryOrderReq(orderPayId, clientType);
        String uri = WeixinConstant.QUERY_ORDER_URI;
        String xml = WeixinUtil.postRequest(uri,qor);
        log.info("weixin queryPayResult return xml="+xml);
        if(xml==null){
            return null;
        }
        QueryOrderReturn qoReturn = XmlUtil.parseObject(xml,QueryOrderReturn.class);
        OrderPay op = orderPayDao.getOrderPayById(orderPayId);
        op.setId(orderPayId);
        op.setPayWay(PayWay.weixin);
        op.setCallbackTime(new Date());
        if(qoReturn.getTimeEnd()!=null){
            op.setSuccTime(DateUtils.parseDate(qoReturn.getTimeEnd(),WeixinUtil.DATE_FORMAT));
        }
        if (qoReturn.getTotalFee() != null) {
            op.setPayAmount(new BigDecimal(qoReturn.getTotalFee()).divide(WeixinUtil.HUNDRUD));
        }
        op.setPayCode(qoReturn.getTradeState());
        op.setPayStatus(transferPayStatus(qoReturn.getTradeState()));
        op.setTradeNo(qoReturn.getTransactionId());
        return op;
    }

    private byte transferPayStatus(String tradeState){
        if(tradeState==null){
            return OrderPay.ORDER_PAY_JYGB;
        }
        switch (tradeState){
            //用户支付中
            case WeixinConstant.PAY_USERPAYING:
                return OrderPay.ORDER_PAY_ZFZ;
            //支付成功
            case WeixinConstant.PAY_SUCCESS:
                //转入退款
            case WeixinConstant.PAY_REFUND:
                return OrderPay.ORDER_PAY_JYCG;
            //未支付
            case WeixinConstant.PAY_NOTPAY:
                //已关闭
            case WeixinConstant.PAY_CLOSED:
                //已撤销（刷卡支付）
            case WeixinConstant.PAY_REVOKED:
                //支付失败(其他原因，如银行返回失败)
            case WeixinConstant.PAY_ERROR:
            default:
                return OrderPay.ORDER_PAY_JYGB;
        }
    }
}