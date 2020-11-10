package cn.jdcloud.medicine.mall.api.biz.pay.vo.weixin;

import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by yanghuoyun on 2017/6/10.
 */
@Data
public class PayResultVo {

    //支付id
    private Integer orderPayId;
    //订单id
    private String orderId;
    //交易金额
    private BigDecimal tradeAmount;
    //支付金额-实付金额
    private BigDecimal payAmount;
    //交易状态
    // SUCCESS—支付成功    REFUND—转入退款    NOTPAY—未支付
    // CLOSED—已关闭    REVOKED—已撤销（刷卡支付）    USERPAYING--用户支付中
    // PAYERROR--支付失败(其他原因，如银行返回失败)
    private String tradeState;
    //支付是否成功 0 否  1是
    private boolean payResult;

    public PayResultVo(){

    }

    public PayResultVo(OrderPay op){
        this.orderId = op.getRelatedId();
        this.orderPayId = op.getId();
        this.tradeAmount = op.getTradeAmount();
        this.payAmount = op.getPayAmount();
        this.tradeState = op.getPayCode();
        if(op.getPayStatus()==OrderPay.ORDER_PAY_JYCG||
                op.getPayStatus()==OrderPay.ORDER_PAY_JYGB){
            this.payResult = true;
        }else{
            this.payResult = false;
        }

    }
}
