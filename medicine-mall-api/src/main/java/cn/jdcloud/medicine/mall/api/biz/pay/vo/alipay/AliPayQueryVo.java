package cn.jdcloud.medicine.mall.api.biz.pay.vo.alipay;

import lombok.Data;

/**
 * @Description: 支付宝sdk-query查询请求参数vo
 * @author: yuanfei
 * @since: 2017/4/18 10:50
 * @version: v1.0
 */
@Data
public class AliPayQueryVo {
    private String out_trade_no;
    private String trade_no;
}