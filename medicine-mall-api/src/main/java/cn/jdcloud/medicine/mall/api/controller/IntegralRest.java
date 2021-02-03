package cn.jdcloud.medicine.mall.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.integral.service.IntegralService;
import cn.jdcloud.medicine.mall.api.biz.integral.service.vo.IntegralVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.domain.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/integral")
@Api(tags = "积分接口")
public class IntegralRest {
	@Autowired
	private IntegralService  integralService;
	@Autowired
	private UserContextUtil userContextUtil;
	@Autowired
	private UserService userService;
	
	
	@ApiOperation(value = "分页查询我的积分")
	@GetMapping(value = "/pageIntegral")
	public ApiResult<Page<IntegralVo>> pageIntegral(@RequestHeader("token") String token,int pageNum,int pageSize,Integer changeType ) {
		Integer userId=userContextUtil.tokenToUserId(token);
		Page<IntegralVo> page=integralService.pageIntegral(pageNum, pageSize, userId, changeType);
		return ApiResult.ok(page);
	}
	
	@ApiOperation(value = "查询我的总积分")
	@GetMapping(value = "/totalIntegral")
	public ApiResult<Integer> totalIntegral(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		User user=userService.getById(userId);
		return ApiResult.ok(user.getIntegral());
	}
	
	
}
