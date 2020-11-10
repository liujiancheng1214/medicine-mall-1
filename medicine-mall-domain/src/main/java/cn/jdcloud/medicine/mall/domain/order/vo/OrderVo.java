package cn.jdcloud.medicine.mall.domain.order.vo;

import cn.jdcloud.medicine.mall.domain.user.UserResult;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单vo
 *
 * @author HuZhengYu
 * @date 15:26 2020/8/22
 */
@Data
public class OrderVo {
        /** 订单id*/
        private String  orderId;
        /** 订单类型：0 普通订单。1拼团订单*/
        private Byte  orderType;
        /** 订单状态: {
         0：待付款
         1：已付款  活动未结束
         2：已付款  待处理
         3：审核资质
         4：正在开单
         5：分拣中
         6：待配送
         7：配送中
         8：整单退款   客户申请退款
         9：整单退款   后台退款
         10：整单退款   拼团失败退款
         11：订单结束   正常结束
         12：订单结束   失败结束
         }*/
        private Byte orderStatus;
        /** 用户id*/
        private Integer userId;
        /** 用户姓名*/
        private String userName;
        /** 商品总数*/
        private Integer totalNum;
        /** 商品总金额*/
        private BigDecimal totalAmount;
        /** 优惠金额*/
        private BigDecimal discountAmount;
        /** 应付金额*/
        private BigDecimal paymentAmount;
        /** 配送方式(1:自提;2:商家直送,3:快递物流)*/
        private Byte deliveryMethod;
        /** 快递公司id*/
        private String expressCompanyId;
        /** 快递公司名称*/
        private String expressCompany;
        /** 快递单号*/
        private String expressNo;
        /** 用户备注*/
        private String userRemark;
        /** 后台备注*/
        private String adminRemark;
        /** 创建时间*/
        private Date createTime;
        /** 修改时间*/
        private Date updateTime;
        /** 客户*/
        private UserResult user;
        /** 订单内容*/
        private List<OrderInfoVO> infoList;

}
