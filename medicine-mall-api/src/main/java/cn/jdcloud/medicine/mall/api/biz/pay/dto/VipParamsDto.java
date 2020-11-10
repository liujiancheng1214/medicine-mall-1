package cn.jdcloud.medicine.mall.api.biz.pay.dto;

import lombok.Data;
/**
 * vip 支付下单参数
 */
@Data
public class VipParamsDto {
    private Integer sellerId;
    private Integer sellerVipId;
}
