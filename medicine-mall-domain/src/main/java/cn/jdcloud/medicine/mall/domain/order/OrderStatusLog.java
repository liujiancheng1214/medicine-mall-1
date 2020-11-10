package cn.jdcloud.medicine.mall.domain.order;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author HuZhengYu
 * @description 订单状态记录表
 * @date 9:07 2020/8/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "t_order_status_log")
public class OrderStatusLog extends BaseDomain {

    /*** 自增id*/
    private Integer id;
    /*** 订单id*/
    private String orderId;
    /*** 操作员id*/
    private Integer adminId;
    /*** 操作员名字*/
    private String adminName;
    /*** 操作*/
    private String action;

    /** 修改时间*/
    @TableField(exist = false)
    private Date updateTime;
    /** 是否删除*/
    @TableField(exist = false)
    private Byte isDeleted;

    public OrderStatusLog() {
    }

    public OrderStatusLog(String orderId, Integer adminId, String adminName, String action, Date createTime) {
        this.orderId = orderId;
        this.adminId = adminId;
        this.adminName = adminName;
        this.action = action;
        this.createTime = createTime;
    }
}
