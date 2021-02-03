package cn.jdcloud.medicine.mall.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarAddVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarUpdateVo;
import cn.jdcloud.medicine.mall.api.biz.share.service.ShareGroupService;
import cn.jdcloud.medicine.mall.api.biz.share.service.ShareService;
import cn.jdcloud.medicine.mall.api.biz.share.vo.ShareGroupUser;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/share")
@Api(tags = "分享接口")
public class ShareRest {
	@Autowired
	private ShareService  shareService;
	@Autowired
	private ShareGroupService  shareGroupService;
	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "新增分享记录,返回1 新增成功")
	@PostMapping(value = "/AddShare")
	public ApiResult<Integer> AddShare(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		shareService.AddShare(userId);
		return ApiResult.ok(1);
	}

	@ApiOperation(value = "新增分享领取,返回1 新增成功")
	@PostMapping(value = "/addShareGroup")
	public ApiResult<Integer> addShareGroup(@RequestHeader("token") String token,Integer  shareId) {
		Integer userId=userContextUtil.tokenToUserId(token);
		shareGroupService.addShareGroup(userId, shareId);
		return ApiResult.ok(1);
	}

	@ApiOperation(value = "查询领取记录")
	@GetMapping(value = "/pageShareGroupUser")
	public ApiResult<Page<ShareGroupUser>> pageShareGroupUser(int pageNum,int pageSize) {
		Page<ShareGroupUser> page=shareGroupService.pageShareGroupUser(pageNum, pageSize);
		return ApiResult.ok(page);
	}
}
