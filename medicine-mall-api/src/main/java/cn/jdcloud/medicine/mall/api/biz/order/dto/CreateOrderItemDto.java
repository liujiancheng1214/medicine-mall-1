package cn.jdcloud.medicine.mall.api.biz.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品项对象")
public class CreateOrderItemDto {
	@ApiModelProperty("商品编码")
    private Integer itemId;      //商品id
	@ApiModelProperty("数量")
    private Integer num;     //数量
	@ApiModelProperty("sku")
    private String sku;
    @ApiModelProperty(value="商品批次号")
    private String itemBatchNo;
}
