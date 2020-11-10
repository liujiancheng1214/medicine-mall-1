package cn.jdcloud.medicine.mall.api.biz.pay.vo.alipay;

import lombok.Data;

/**
 * @Description: 支付宝支付 订单信息vo
 * @author: yuanfei
 * @since: 2017/4/20
 * @version: v1.0
 */
@Data
public class OrderInfoVo {
    private String orderInfo;
    private String outTradeNo;
}