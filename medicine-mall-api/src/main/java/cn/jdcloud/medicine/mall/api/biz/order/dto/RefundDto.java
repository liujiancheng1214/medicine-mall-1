package cn.jdcloud.medicine.mall.api.biz.order.dto;

import lombok.Data;

/**
 * @ClassName RefuseRefundDto
 * @Author wuzhiyong
 * @Date 2020/8/25 8:39
 * @Version 1.0
 **/
@Data
public class RefundDto {
    /** 订单id */
    private String orderId;
    /** 退款原因id  */
    private Integer reason;
    /** 退款类型*/
    private Integer refundType;
    /** 备注*/
    private String remark;
}
