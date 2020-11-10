package cn.jdcloud.medicine.mall.domain.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息vo
 *
 * @author HuZhengYu
 * @date 14:11 2020/8/22
 */
@Data
public class OrderInfoVO {
    /** id*/
    private Integer id;
    /** 订单id*/
    private String orderId;
    /** 商品Id*/
    private Integer itemId;
    /** 商品名称*/
    private String itemName;
    /** 商品生产批号*/
    private String itemBatchNo;
    /** 商品数量*/
    private Integer itemNum;
    /** 商品图标*/
    private String itemIcon;
    /** 购买单价*/
    private BigDecimal itemPrice;
    /** 总价格*/
    private BigDecimal totalPrice;
    /**商品编码*/
    private String itemNo;
    /**商品规格*/
    private String spec;
    /**生产厂家*/
    private String factory;
    /** 退款数量*/
    private Integer refundNum;
    /** 退款金额*/
    private BigDecimal refundPrice;
    /** 商品库存*/
    private BigDecimal qty;
    /** 有效期截止日*/
    private Date validDay;
}
