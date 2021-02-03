package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.util.List;

import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionItemUserVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PromotionItemDetailVo {

	private ItemVo itemVo;
	@ApiModelProperty("收藏标识 0 未收藏 1 已收藏")
	private int collectionTag;
	@ApiModelProperty("团购状态")
	private String status;
	@ApiModelProperty("还剩多少时间结束")
	private long surplusTime;
	@ApiModelProperty("成团数量")
	private String successDesc;
	@ApiModelProperty("成团数量")
	private Integer successNum;
	@ApiModelProperty("商品对应的推荐信息")
	private List<ItemVo> recommendList;
	private List<PromotionItemUserVo> userList;
}
