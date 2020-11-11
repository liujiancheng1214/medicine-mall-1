package cn.jdcloud.medicine.mall.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserCenterVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.domain.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserRest {


	@Autowired
	private UserContextUtil userContextUtil;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "发送手机验证码")
	@GetMapping(value = "/sendCode")
	public void sendCode(String mobile) {
		String code="123456";
		userContextUtil.saveCode(mobile, code);
	}

	@ApiOperation(value = "手机验证码校验 通过1  不通过 0")
	@GetMapping(value = "/checkCode")
	public  ApiResult<Integer>checkCode(String mobile,String code) {
		if(userContextUtil.checkCode(mobile, code)) {
			return ApiResult.ok(1);
		}
		return ApiResult.ok(0);
	}
	@ApiOperation(value = "判断手机号码是否已存在,1 存在  0 不存在")
	@GetMapping(value = "/check")
	public ApiResult<Integer> queryUserByMobil(String mobile) {
		User  user=userService.queryUserByMobile(mobile);
		if(user==null) {
			return ApiResult.ok(1);
		}
		return ApiResult.ok(0);
	}

	@ApiOperation(value = "用户登录,返回token")
	@GetMapping(value = "/login")
	public ApiResult<String> login(String mobile,String password) {
		User  user=userService.login(mobile, password);
		String token=userContextUtil.userIdToToken(user.getId());
		return ApiResult.ok(token);
	}


	@ApiOperation(value = "用户注册")
	@PostMapping(value = "/regist")
	public ApiResult<User> login(@RequestBody UserAddVo  userAddVo) {
		// TODO 验证码校验
		User  user=userService.regist(userAddVo);
		return ApiResult.ok(user);
	}

	@ApiOperation(value = "用户退出")
	@GetMapping(value = "/loginOut")
	public void loginOut(@RequestHeader("token") String token) {
		userContextUtil.loginOut(token);
	}

	 @ApiOperation(value = "用户信息查询")
	 @GetMapping(value = "/userInfo")
	 public ApiResult<UserCenterVo> userInfo(@RequestHeader("token") String token) {
		Integer userId = userContextUtil.tokenToUserId(token);
		UserCenterVo userCenterVo= userService.queryUserCenter(userId);
		 return ApiResult.ok(userCenterVo);
	 }
}
