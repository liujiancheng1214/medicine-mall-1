package cn.jdcloud.medicine.mall.api.biz.order.vo;

import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.order.OrderInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ToPayOrderVo {
        /** 订单id*/
        private String  orderId;
        /** 买家id*/
        private Integer buyerId;
        //收件人名字
        private String receiverName;
        //收件人电话
        private String receiverPhone;
        //收货地址
        private String address;
        /** 收货地址*/
        private Integer addressId;
        /** 卖家id*/
        private Integer sellerId;
        /** 卖家名字*/
        private String sellerName;
        /** 商品总数*/
        private Integer totalNum;
        /** 应付总金额*/
        private BigDecimal totalAmount;
        /** 订单内容*/
        private List<OrderInfo> infoList;

        private byte orderStatus;

        public ToPayOrderVo(){

        }

        public ToPayOrderVo(Order order, List<OrderInfo> list) {
                this.orderId = order.getOrderId();
                this.totalNum = order.getTotalNum();
                this.totalAmount = order.getTotalAmount();
                this.orderStatus = order.getOrderStatus();
                this.infoList = list;
        }
}
