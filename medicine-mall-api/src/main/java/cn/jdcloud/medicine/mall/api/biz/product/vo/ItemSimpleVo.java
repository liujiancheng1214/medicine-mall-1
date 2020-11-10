package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ItemSimpleVo {
	  private Integer id;
	    /**商品编码*/
	    @ApiModelProperty(value="商品编码")
	    private String itemNo;
	    /**商品名字*/
	    @ApiModelProperty(value="商品名字")
	    private String itemName;
	    /**商品品牌ID*/
	    @ApiModelProperty(value="商品品牌ID")
	    private Integer itemBrandId;
	    @ApiModelProperty(value="商品品牌")
	    private String itemBrandName;
	    /**生产厂家*/
	    private String factory;
	    /**市场价格*/
	    private BigDecimal retailPrice;
	    /**平台价格*/
	    private BigDecimal platformPrice;
	    /**平台团购价格*/
	    private BigDecimal promotionPrice;
	    /**生产日期 */
	    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	    private Date productionDate;
	    // 有效日期  生产日期+有效月
	    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	    private Date effectiveDate;
	    /**产品封面*/
	    @ApiModelProperty(value="产品封面")
	    private String imgCover;
	    @ApiModelProperty(value="单价")
	    private BigDecimal price;
	    @ApiModelProperty(value="数量")
	    private int num;
	    @ApiModelProperty(value="是否团购、1 是  0 不是")
	    private int promotionTag;

}
