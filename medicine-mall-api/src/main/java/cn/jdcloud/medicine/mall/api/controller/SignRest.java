package cn.jdcloud.medicine.mall.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.sign.SignService;
import cn.jdcloud.medicine.mall.api.biz.sign.vo.SignVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sign")
@Api(tags = "签到接口")
public class SignRest {
	@Autowired
	private SignService  signService;
	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "签到，返回1 签到成功")
	@PostMapping(value = "/addSign")
	public ApiResult<Integer> addSign(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		signService.addSign(userId);
		return ApiResult.ok(1);
	}

	@ApiOperation(value = "签到信息查询")
	@PostMapping(value = "/deleteCar")
	public ApiResult<SignVo> querySignVo(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		SignVo  signVo=signService.querySignVo(userId);
		return ApiResult.ok(signVo);
	}

	
	@ApiOperation(value = "更新签到通知开关 0 关 1 开 ,返回1 更新成功")
	@GetMapping(value = "/updateSignTag")
	public ApiResult<Integer> updateSignTag(@RequestHeader("token") String token,byte tag) {
		Integer userId=userContextUtil.tokenToUserId(token);
		signService.updateUserSignTag(userId, tag);
		return ApiResult.ok(1);
	}

}
