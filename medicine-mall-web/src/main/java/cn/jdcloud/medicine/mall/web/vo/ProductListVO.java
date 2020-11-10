package cn.jdcloud.medicine.mall.web.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品列表")
public class ProductListVO {


    private Integer id;

    /**商品编码*/
    @ApiModelProperty(value="商品编码")
    private String itemNo;
    /**商品名字*/
    @ApiModelProperty(value="商品名称")
    private String itemName;
    /**商品分类ID */
    @ApiModelProperty(value="商品分类ID")
    private Integer itemCategoryId;
    /**商品品牌ID*/
    @ApiModelProperty(value="商品品牌ID")
    private Integer itemBrandId;
    /**产品正面 */
    @ApiModelProperty(value="产品正面 ")
    private String imgFront;
    /**生产厂家*/
    @ApiModelProperty(value="生厂厂家")
    private String factory;
    /**产地*/
    @ApiModelProperty(value="产地")
    private String originPlace;
    /**零售价格*/
    @ApiModelProperty(value="零售价格")
    private BigDecimal retailPrice;
    /**单位*/
    @ApiModelProperty(value="单位")
    private String unit;
    /**总销售数量*/
    @ApiModelProperty("已售")
    private Integer subtotalSaleNum;
    @ApiModelProperty(value="有效期")
    private Integer expiryDate;
}
