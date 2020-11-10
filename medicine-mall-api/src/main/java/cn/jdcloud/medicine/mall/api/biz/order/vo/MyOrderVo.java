package cn.jdcloud.medicine.mall.api.biz.order.vo;

import lombok.Data;

@Data
public class MyOrderVo {

    private String orderId;
    private Integer courseId;
    private String courseImg;
    private String courseName;
    private String createTime;
    private String statusStr;
    private String amount;

    private String status;
    private Boolean isFree;

}
