package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PageItemVo {
	    private Integer id;
	    /**商品编码*/
	    @ApiModelProperty(value="商品编码")
	    private String itemNo;
	    /**商品名字*/
	    @ApiModelProperty(value="商品名字")
	    private String itemName;
	    /**产品封面*/
	    @ApiModelProperty(value="产品封面")
	    private String imgCover;
	    /**商品分类ID */
	    @ApiModelProperty(value="String")
	    private Integer itemCategoryId;
	    /**商品品牌ID*/
	    @ApiModelProperty(value="商品品牌ID")
	    private Integer itemBrandId;
	    /**商品品牌ID*/
	    @ApiModelProperty(value="商品品牌")
	    private String itemBrandName;
	    /**批准文号有效期 */
	    @ApiModelProperty(value="有效期")
	    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	    private Date effectiveDate;
	    @ApiModelProperty(value="数量")
	    private Integer num;
	    @ApiModelProperty(value="市场单价")
	    private BigDecimal retailPrice;
	    
	    @ApiModelProperty(value="厂家")
	    private String factory;
	    
	    @ApiModelProperty(value="平台单价")
	    private BigDecimal platformPrice;
	    @ApiModelProperty(value="sku")
	    private String sku;
	    @ApiModelProperty(value="商品批次号")
	    private String itemBatchNo;
	    
	    private Integer carId;
}
