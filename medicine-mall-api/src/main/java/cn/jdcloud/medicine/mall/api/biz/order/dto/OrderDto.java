package cn.jdcloud.medicine.mall.api.biz.order.dto;

import lombok.Data;

/**
 * @author qun.xu
 * @desc
 * @date   2018.09.17
 */
@Data
public class OrderDto {

    private String  orderId;
    /** 订单状态*/
    private Byte orderStatus;

    private Integer expressCompanyId;

    /** 开始时间*/
    private String startDate;
    /** 结束时间*/
    private String endDate;
    /** 搜索关键字*/
    private String searchValue;
    /** 订单编号*/
    private String orderNo;
    /** 药店/药店编码*/
    private String company;
    /** 手机号码*/
    private String mobile;
    /** 订单id数组*/
    private String[] orderIds;

}
