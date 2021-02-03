package cn.jdcloud.medicine.mall.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.service.SearchService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.FilterVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.SearchItem;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.domain.product.Search;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/search")
@Api(tags = "搜索接口")
public class SearchRest {
	@Autowired
	private ItemService  itemService;
	@Autowired
	private SearchService  searchService;
	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "删除历史搜索,返回1 删除成功")
	@DeleteMapping(value = "/deleteUserSearch")
	public ApiResult<String> deleteUserSearch(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		searchService.deleteSearchByUserId(userId);
		return ApiResult.ok("1");
	}
	
	@ApiOperation(value = "历史搜索")
	@GetMapping(value = "/userSearch")
	public ApiResult<List<Search>> userSearch(@RequestHeader("token") String token) {
		Integer userId=userContextUtil.tokenToUserId(token);
		List<Search> list = searchService.listSearchByUserId(userId);
		return ApiResult.ok(list);
	}

	@ApiOperation(value = "常用商品")
	@PostMapping(value = "/deleteCar")
	public ApiResult<List<SearchItem>> deleteCar() {
		List<SearchItem>  list=searchService.listcommonlyUsedItem();
		return ApiResult.ok(list);
	}

	@ApiOperation(value = "平台热卖")
	@GetMapping(value = "/listhotItem")
	public ApiResult<CarItemVo> listhotItem() {
		List<SearchItem>  list=searchService.listhotItem();
		return ApiResult.ok(list);
	}

	
	@ApiOperation(value = "筛选")
	@GetMapping(value = "/filter")
	public ApiResult<FilterVo> filter() {
		FilterVo filterVo=itemService.queryItemFilterInfo();
		return ApiResult.ok(filterVo);
	}


}
