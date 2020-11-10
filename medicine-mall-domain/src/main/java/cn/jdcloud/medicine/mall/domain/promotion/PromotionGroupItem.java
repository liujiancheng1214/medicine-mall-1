package cn.jdcloud.medicine.mall.domain.promotion;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_promotion_group_item")
public class PromotionGroupItem extends BaseDomain {
    private Integer id;
    /**活动id*/
    private Integer promotionId;
    /**商品编码*/
    private String itemNo;
    /**商品团购单价*/
    private BigDecimal itemGroupPrice;
    /**单份数量*/
    private Integer itemNum;
    /**活动参与数量类别(1:按实际库存;2:按固定数量)*/
    private Byte totalItemType;
    /**参与活动商品总数量*/
    private Integer totalItemNum;
    /**参与活动商品剩余数量*/
    private Integer totalItemQty;
    
    private String sku;
    /**
     * 是否删除 0否 1是
     */
    @TableField(exist = false)
    protected Byte isDeleted;
}
