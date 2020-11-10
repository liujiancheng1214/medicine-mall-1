package cn.jdcloud.medicine.mall.api.biz.pay.vo.alipay;

import lombok.Data;

/**
 * @Description: 支付宝支付 客户端同步验证-resultVO
 * @author: yuanfei
 * @since: 2017/4/9 10:50
 * @version: v1.0
 */
@Data
public class ClientResultVO<AlipayTradeAppPayResponseVO> {
    private AlipayTradeAppPayResponseVO alipay_trade_app_pay_response;        // 客户端同步验证-alipay_trade_app_pay_responseVO
    private String sign;
    private String sign_type;
}
