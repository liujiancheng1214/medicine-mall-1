package cn.jdcloud.medicine.mall.domain.order;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 退款单
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_refund_order")
public class RefundOrder extends BaseDomain {

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
    public static final Byte STATUS_SQTK_HT = 1;
    public static final Byte STATUS_SQTK_KH = 2;
    public static final Byte STATUS_DQR = 3;
    public static final Byte STATUS_DSH = 4;
    public static final Byte STATUS_TKZ = 5;
    public static final Byte STATUS_YTK = 6;
    public static final Byte STATUS_TKSB = 7;
    public static final Byte STATUS_KHJJ = 8;

    @TableId(value = "order_id",type = IdType.INPUT)
    private java.lang.String id;

    /**
     * 订单id
     */
    private java.lang.String orderId;

    /**
     * 用户id
     */
    private java.lang.Integer userId;

    /**
     * 退款原因 t_sys_dict 表 type REFUND_REASON
     */
    private Byte reason;

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
     * 结算状态 1 未结算  2 结算中 3 已结算
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
     * 退款时间
     */
    private Date applyTime;

    /**
     * 退款时间
     */
    private java.util.Date refundTime;

    @TableField(exist = false)
    protected Byte isDeleted;

}
