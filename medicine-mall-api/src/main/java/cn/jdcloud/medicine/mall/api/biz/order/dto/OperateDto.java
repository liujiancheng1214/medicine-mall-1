package cn.jdcloud.medicine.mall.api.biz.order.dto;

import lombok.Data;
/**
 * @ClassName RefuseRefundDto
 * @Author wuzhiyong
 * @Date 2020/8/25 8:39
 * @Version 1.0
 **/
@Data
public class OperateDto {
    /**  ids */
    private Integer[] ids;
    /** 订单ids */
    private String[] orderIds;
    /** 备注  */
    private String remark;
    /** 如果 操作发快递物流 填写 快递公司id*/
    private String expressCompanyId;
    /** 如果 操作发快递物流 填写 快递单号*/
    private String[] expressNo;
}
