package cn.jdcloud.medicine.mall.domain.product;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_item_batch")
public class ItemBatch {
	  //删除的
    public static final byte DELETE = 1;
    //未删除的
    public static final byte UNDELETE = 0;
    private Integer id;
    /**商品编码*/
    private String itemNo;
    /**生产批号*/
    private String batchNo;
    /**生产日期*/
    private Date producedTime;
    /**售价  用户购买时需要选择批号*/
    private BigDecimal price;
    /**库存*/
    private BigDecimal qty;
    /**用于计算价格*/
    private String sku;
    
    private  Byte isDel;
    
    // SKU生成策略
    public String generateSKU() {
    	return itemNo+"-"+batchNo;
    }
    
}
