package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderService;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderAddressVo;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderListVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.dao.order.OrderMapper;
import cn.jdcloud.medicine.mall.domain.order.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = "订单相关接口")
public class OrderRest {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserContextUtil userContextUtil;
	@ApiOperation(value = "创建订单")
	@PostMapping(value = "/createOrder")
	public ApiResult<String> createOrder(@RequestHeader("token") String token,@RequestBody CreateOrderDto createOrderDto) {
		Integer userId=userContextUtil.tokenToUserId(token);
		String orderId=orderService.createOrder(userId, createOrderDto);
		return ApiResult.ok(orderId);
	}

	@ApiOperation(value = "查询我的订单列表 状态不传标识查询全部状态订单")
	@GetMapping(value = "/orderList")
	public ApiResult<List<OrderListVo>> orderList(@RequestHeader("token") String token,int pageNum,int pageSize,Byte  orderState) {
		Integer userId=userContextUtil.tokenToUserId(token);
		List<OrderListVo> list=orderService.pageOrderList(pageNum, pageSize, userId,null, orderState);
		return ApiResult.ok(list);
	}

	@ApiOperation(value = "查询订单详情")
	@GetMapping(value = "/orderDetail")
	public ApiResult<OrderListVo> orderDetail(@RequestHeader("token") String token,String orderId) {
		Integer userId=userContextUtil.tokenToUserId(token);
		List<OrderListVo> list=orderService.pageOrderList(1, 1, userId,orderId, null);
		if(list.size()>0) {
			return ApiResult.ok(list.get(0));
		}
		return ApiResult.ok(null);
	}




	@ApiOperation(value = "查询订单地址")
	@GetMapping(value = "/queryOrderAddress")
	public ApiResult<OrderAddressVo>  queryOrderAddress(@RequestHeader("token") String token,String orderId) {
		Integer userId=userContextUtil.tokenToUserId(token);
		OrderAddressVo vo=new OrderAddressVo();
		Order order=orderMapper.selectById(orderId);
		if(order!=null&&order.getUserId().equals(userId)) {
			BeanUtil.copyProperties(order, vo);
		}
		return ApiResult.ok(vo);
	}

	@ApiOperation(value = "更新订单地址")
	@PostMapping(value = "/updateOrderAddress")
	public ApiResult<String> updateOrderAddress(@RequestHeader("token") String token,@RequestBody OrderAddressVo orderAddressVo) {
		Integer userId=userContextUtil.tokenToUserId(token);
		Order order=orderMapper.selectById(orderAddressVo.getOrderId());
		if(order!=null&&order.getUserId().equals(userId)) {
			BeanUtil.copyProperties(orderAddressVo, order);
			int i=orderMapper.updateById(order);
		}

		return ApiResult.ok(orderAddressVo.getOrderId());
	}


}
