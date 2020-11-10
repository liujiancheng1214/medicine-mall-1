package cn.jdcloud.medicine.mall.api.biz.product.vo;

import cn.jdcloud.medicine.mall.domain.product.Car;
import cn.jdcloud.medicine.mall.domain.product.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CarItemVo {
	@ApiModelProperty("购物车对象")
	private Car car;
	@ApiModelProperty("商品信息")
	private ItemVo  itemVo;
//	@ApiModelProperty("购物车对应商品对象")
//	private ItemDetailVo itemDetailVo;
}
