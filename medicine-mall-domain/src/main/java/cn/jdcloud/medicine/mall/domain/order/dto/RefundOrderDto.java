package cn.jdcloud.medicine.mall.domain.order.dto;

import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * 退款单
 */
@Data
public class RefundOrderDto extends BaseDomain {
    /**
     * id
     */
    private String id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 应退还总金额
     */
    private java.math.BigDecimal totalRefundAmount;

    /**
     * 实际退还金额
     */
    private java.math.BigDecimal actualRefundAmount;

    /**
     * 退款药品总数量
     */
    private Integer totalNum;
    /**
     * 1:申请退款 后台申请
     * 2:申请退款 客户申请
     * 3:待客户确认（后台申请的订单）
     * 4:待审核  （客户申请的订单）
     * 5:退款中
     * 6:已退款
     * 7:退款失败
     * 8:客户拒绝
     */
    private  Byte status;

    /**
     * 结算状态
     */
    private Byte settleStatus;

    /**
     * 后台 备注
     */
    private String remarkAdmin;

    /**
     * 客户（药店备注）
     */
    private String remarkUser;

    /**
     * 退款类型 1 整单退款  2 部分退款
     */
    private Byte refundType;

    /**
     * 申请退款时间
     */
    private Date applyTime;

    /**
     * 退款时间
     */
    private Date refundTime;

    private List<RefundOrderInfoDto> infoList;

    protected Date createTime;
    protected Date updateTime;
    protected Byte isDeleted;

    private String companyName;

}
