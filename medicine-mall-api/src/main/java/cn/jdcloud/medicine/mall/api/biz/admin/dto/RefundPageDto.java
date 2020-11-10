package cn.jdcloud.medicine.mall.api.biz.admin.dto;

import lombok.Data;

import java.util.Date;
/**
 * @ClassName RefundPageDto
 * @Author wuzhiyong
 * @Date 2020/8/24 9:47
 * @Version 1.0
 **/
@Data
public class RefundPageDto {
    /**
     * 搜索关键字
     */
    private String search;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 退款时间-起始点
     */
    private String refundTimeBegin;
    /**
     * 退款时间-终止点
     */
    private String refundTimeEnd;
    /**
     * 下单时间-起始点
     */
    private String orderTimeBegin;
    /**
     * 下单时间-终止点
     */
    private String orderTimeEnd;
    /**
     * 结算状态
     */
    private Byte settleStatus;
    /**
     * 结算状态
     */
    private Byte refundReason;
}
