package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.service.CarService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarAddVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarUpdateVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@Api(tags = "购物车接口")
public class CarRest {
	@Autowired
	private CarService  carService;
	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "添加购物车")
	@PostMapping(value = "/addCar")
	public ApiResult<CarItemVo> addCar(@RequestHeader("token") String token,@RequestBody CarAddVo carAddVo) {
		Integer userId=userContextUtil.tokenToUserId(token);
		CarItemVo carItemVo = carService.addCar(userId, carAddVo);
		return ApiResult.ok(carItemVo);
	}

	@ApiOperation(value = "删除购物车,返回1 删除成功")
	@PostMapping(value = "/deleteCar")
	public ApiResult<Integer> deleteCar(@RequestHeader("token") String token,Integer carId) {
		Integer userId=userContextUtil.tokenToUserId(token);
		int i=carService.deleteCar(userId, carId);
		return ApiResult.ok(i);
	}

	@ApiOperation(value = "修改购物车数量")
	@PostMapping(value = "/updateCarNum")
	public ApiResult<CarItemVo> updateCarNum(@RequestHeader("token") String token,@RequestBody CarUpdateVo carUpdateVo) {
		Integer userId=userContextUtil.tokenToUserId(token);
		CarItemVo carItemVo=carService.updateCarNum(userId, carUpdateVo);
		return ApiResult.ok(carItemVo);
	}

	@ApiOperation(value = "分页查询我的购物车列表")
	@GetMapping(value = "/listCar")
	public ApiResult<List<CarItemVo>> listCar(@RequestHeader("token") String token,int pageNum,int pageSize) {
		Integer userId=userContextUtil.tokenToUserId(token);
		List<CarItemVo> list=carService.listCarListByUserId(userId, pageNum, pageSize);
		return ApiResult.ok(list);
	}


}
