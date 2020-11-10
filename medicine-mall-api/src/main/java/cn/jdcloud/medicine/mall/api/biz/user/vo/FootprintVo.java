package cn.jdcloud.medicine.mall.api.biz.user.vo;

import java.util.List;

import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import lombok.Data;


@Data
public class FootprintVo {

	private String time;
	
	List<ItemVo> itemList;
}
