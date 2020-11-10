package cn.jdcloud.medicine.mall.domain.order;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_order_info")
public class OrderInfo extends BaseDomain {
    //订单内容 id
    private Integer id;
    /** 订单id*/
    private String orderId;
    //客户id
    private Integer userId;
    //商品Id
    private Integer itemId;
    /** 商品生产批号*/
    private String itemBatchNo;

    private String sku;
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

    /** 是否删除 0否 1是*/
    @TableField(exist = false)
    protected Byte isDeleted;

}
