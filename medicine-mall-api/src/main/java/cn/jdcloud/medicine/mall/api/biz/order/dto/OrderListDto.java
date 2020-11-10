package cn.jdcloud.medicine.mall.api.biz.order.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderListDto {

	    private String  orderId;
	    /** 订单状态*/
	    private Byte orderStatus;
	    private Integer userId;
	    /** 订单id数组*/
	    private List<String> orderIds;
}
