package cn.jdcloud.medicine.mall.domain.order.dto;

import cn.jdcloud.framework.core.common.BaseDomain;
import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.product.Item;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RefundOrderInfoDto extends BaseDomain{

    private Integer id;
    /** 退款单id*/
    private Integer refundOrderId;
    //商品Id
    private Integer itemId;
    //商品数量
    private Integer itemNum;
    //'商品名称'
    private String itemName;
    //商品图标
    private String itemIcon;
    //购买单价
    private BigDecimal itemPrice;
    //总价格
    private BigDecimal totalPrice;

    protected Date createTime;
    protected Date updateTime;
    protected Byte isDeleted;

    //商品编号
    private String itemNo;
    //商品助记码
    private String helpCode;
    //商品规格
    private String spec;
    //生产厂家
    private String factory;
    //单位
    private String unit;
    //批准文号
    private String approvalNo;
    //有效期（月）
    private Integer expiryDate;
    //库存
    private Integer qty;

}
