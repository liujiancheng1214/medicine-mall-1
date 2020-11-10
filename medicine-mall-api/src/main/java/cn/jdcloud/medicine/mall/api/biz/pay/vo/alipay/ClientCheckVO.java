package cn.jdcloud.medicine.mall.api.biz.pay.vo.alipay;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: 支付宝支付 客户端同步验证VO
 * @author: yuanfei
 * @since: 2017/4/9 10:50
 * @version: v1.0
 */
@Data
@ToString
public class ClientCheckVO {
    private String memo;                        // 消息描述
    private String resultStatus;                // 结果码
    private String result;                      // 客户端同步验证-resultVO
    private String outTradeNo;                  // 订单支付id
}