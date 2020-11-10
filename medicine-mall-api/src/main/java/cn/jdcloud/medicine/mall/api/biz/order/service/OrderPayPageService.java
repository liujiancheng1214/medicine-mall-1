package cn.jdcloud.medicine.mall.api.biz.order.service;

import java.util.List;

import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderPayPageVO;

public interface OrderPayPageService {

	OrderPayPageVO payPageByItem(Integer userId,String itemNO,int  promotionTag,Integer promotionId,Integer groupInfoId,String sku,int num);
	
	
	OrderPayPageVO payPageByCar(Integer userId,List<String> carIds);
	
	OrderPayPageVO payPageByOrder(Integer userId,String orderNo);
}
