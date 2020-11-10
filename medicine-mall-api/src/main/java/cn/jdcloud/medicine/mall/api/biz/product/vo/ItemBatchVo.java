package cn.jdcloud.medicine.mall.api.biz.product.vo;

import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("产品批次")
public class ItemBatchVo {
	private Integer id;
	/**商品编码*/
	@ApiModelProperty(value="商品编码")
	private String itemNo;
	/**生产批号*/
	@ApiModelProperty(value="生产批号")
	private String batchNo;
	/**生产日期*/
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@ApiModelProperty(value="生产日期")
	private Date producedTime;
	/**库存*/
	@ApiModelProperty(value="库存")
	private BigDecimal qty;

	@ApiModelProperty(value="sku")
	private String sku;

	@ApiModelProperty(value="价格")
    private BigDecimal price;

    public ItemBatchVo() {}
	public ItemBatchVo(ItemBatch batch){
		this.id = batch.getId();
		this.itemNo = batch.getItemNo();
		this.batchNo = batch.getBatchNo();
		this.producedTime = batch.getProducedTime();
		this.sku=batch.getSku();
		this.price=batch.getPrice();
		this.qty = batch.getQty();
	}
}
