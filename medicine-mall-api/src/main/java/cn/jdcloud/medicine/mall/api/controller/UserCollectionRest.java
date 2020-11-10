package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserCollectionService;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/userCollection")
@Api(tags = "我的收藏接口")
public class UserCollectionRest {

	@Autowired
	private UserCollectionService  userCollectionService;
	@Autowired
	private UserContextUtil userContextUtil;
	@ApiOperation(value = "分页查询我的收藏列表")
	@GetMapping(value = "/listUserCollection")
	public ApiResult<List<ItemVo>> listUserCollection(@RequestHeader("token") String token, int pageNum, int pageSize) {
		Integer userId=userContextUtil.tokenToUserId(token);
		List<ItemVo> list= userCollectionService.listUserCollection(userId, pageNum, pageSize);
		return ApiResult.ok(list);
	}
	@ApiOperation(value = "删除我的收藏,返回删除记录数")
	@GetMapping(value = "/deleteUserCollection")
	public ApiResult<Integer> deleteUserCollection(@RequestHeader("token") String token,String itemNos) {
		Integer userId=userContextUtil.tokenToUserId(token);
		int i= userCollectionService.deleteUserCollection(userId, Arrays.asList(itemNos.split(",")));
		return ApiResult.ok(i);
	}

	@ApiOperation(value = "增加我的收藏,返回1 增加成功")
	@GetMapping(value = "/addUserCollection")
	public ApiResult<Integer> addUserCollection(@RequestHeader("token") String token,String itemNo) {
		Integer userId=userContextUtil.tokenToUserId(token);
		int i= userCollectionService.addUserCollection(userId, itemNo);
		return ApiResult.ok(i);
	}

}
