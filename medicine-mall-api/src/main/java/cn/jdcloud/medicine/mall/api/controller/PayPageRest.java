package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderPayPageService;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderPayPageVO;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/payPage")
@Api(tags = "进入支付页接口")
public class PayPageRest {

	@Autowired
	private OrderPayPageService orderPayPageService;

	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "选中商品直接进去支付页")
	@GetMapping(value = "/payPageByItem")
	public ApiResult<OrderPayPageVO> payPageByItem(@RequestHeader("token") String token, String itemNo, int promotionTag, Integer promotionId, Integer groupInfoId,
												   String sku, int num) {
		Integer userId=userContextUtil.tokenToUserId(token);
		OrderPayPageVO orderPayPageVO = orderPayPageService.payPageByItem(userId, itemNo,promotionTag,promotionId,groupInfoId,sku, num);
		return ApiResult.ok(orderPayPageVO);
	}

	@ApiOperation(value = "购物车进入支付页")
	@GetMapping(value = "/payPageByCar")
	public ApiResult<OrderPayPageVO> payPageByCar(@RequestHeader("token") String token,String  carIds) {
		Integer userId=userContextUtil.tokenToUserId(token);
		OrderPayPageVO orderPayPageVO = orderPayPageService.payPageByCar(userId, Arrays.asList(carIds.split(",")));
		return ApiResult.ok(orderPayPageVO);
	}

}
