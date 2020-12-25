package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PresaleItemVo {
	    private int itemId;
	    @ApiModelProperty(value="商品编码")
	    private String itemNo;
	    /**商品名字*/
	    @ApiModelProperty(value="商品名字")
	    private String itemName;
	    /**生产厂家*/
	    @ApiModelProperty(value="生产厂家")
	    private String factory;
	    /**单位*/
	    @ApiModelProperty(value="单位")
	    private String unit;
	    
	    @ApiModelProperty(value="平台价格")
	    private BigDecimal platformPrice;
	    
	    @ApiModelProperty(value="零售价格")
	    private BigDecimal retailPrice;
	    
	    private Integer limitNum;
	    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	    @ApiModelProperty(value="发货时间")
	    private Date sendDate;
	    @ApiModelProperty(value="品牌名称")
	    private String brandName;
	    @ApiModelProperty(value="类别名称")
	    private String catogeryName;
}
