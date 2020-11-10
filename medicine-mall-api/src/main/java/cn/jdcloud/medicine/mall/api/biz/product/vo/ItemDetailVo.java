package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品详情VO")
public class ItemDetailVo {
	@ApiModelProperty("商品信息")
	private ItemVo  itemVo;
	@ApiModelProperty("商品对应的推荐信息")
	private List<ItemVo> recommendList;
	@ApiModelProperty("收藏标识 0 未收藏 1 已收藏")
	private int collectionTag;
}
