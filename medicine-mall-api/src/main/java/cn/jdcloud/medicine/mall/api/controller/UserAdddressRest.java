package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserAddressService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressAddVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressUpdateVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Api(tags = "地址模块接口")
public class UserAdddressRest {

	@Autowired
	private UserAddressService userAddressService;
	@Autowired
	private UserContextUtil userContextUtil;


	@ApiOperation(value = "新增地址")
	@PostMapping(value = "/addUserAddress")
	public ApiResult<UserAddressVo> addUserAddress(@RequestHeader("token") String token,@RequestBody UserAddressAddVo userAddressAddVo) {
		Integer userId=userContextUtil.tokenToUserId(token);
		UserAddressVo userAddressVo = userAddressService.addUserAddressVo(userId, userAddressAddVo);
		return ApiResult.ok(userAddressVo);
	}

	@ApiOperation(value = "更新地址")
	@PostMapping(value = "/updateUserAddress")
	public ApiResult<UserAddressVo> updateUserAddress(@RequestHeader("token") String token,@RequestBody UserAddressUpdateVo userAddressUpdateVo) {
		Integer userId=userContextUtil.tokenToUserId(token);
		UserAddressVo userAddressVo=userAddressService.updateUserAddress(userId, userAddressUpdateVo);
		return ApiResult.ok(userAddressVo);
	}

	@ApiOperation(value = "设置默认地址")
	@GetMapping(value = "/setDefaultAddress")
	public ApiResult<UserAddressVo> setDefaultAddress(@RequestHeader("token") String token,Integer addressId) {
		Integer userId=userContextUtil.tokenToUserId(token);
		UserAddressVo userAddressVo = userAddressService.setDefaultAddress(userId, addressId);
		return ApiResult.ok(userAddressVo);
	}

	@ApiOperation(value = "删除地址,返回1 删除成功")
	@GetMapping(value = "/deleteUserAddress")
	public ApiResult<Integer> deleteUserAddress(@RequestHeader("token") String token,Integer addressId) {
		Integer userId=userContextUtil.tokenToUserId(token);
		int i = userAddressService.deleteUserAddress(userId, addressId);
		return ApiResult.ok(i);
	}

	@ApiOperation(value = "地址列表")
	@GetMapping(value = "/listUserAddress")
	public ApiResult<List<UserAddressVo>> listUserAddress(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		List<UserAddressVo> list = userAddressService.listUserAddressByUserId(userId);
		return ApiResult.ok(list);
	}

}
