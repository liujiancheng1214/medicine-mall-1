package cn.jdcloud.medicine.mall.api.biz.pay.dto;

import lombok.Data;

/**
 * 统一下单入参
 */
@Data
public class OrderParamsDto {

    //业务关联id
    private String relatedId;
    //支付类型 1 商品  2vip 3 退款
    private Byte payType;
    //收货地址id
    private Integer addressId;




}
