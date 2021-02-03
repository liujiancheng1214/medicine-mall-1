package cn.jdcloud.medicine.mall.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.medicine.mall.api.biz.config.ConfigService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.service.SearchService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PromotionItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.FootprintService;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.api.constant.Constant;
import cn.jdcloud.medicine.mall.api.params.ProductQueryParam;
import cn.jdcloud.medicine.mall.dao.product.ItemBrandMapper;
import cn.jdcloud.medicine.mall.domain.config.Config;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/item")
@Api(tags = "商品接口")
public class ItemRest {


	@Autowired
	private ItemService itemService;
	@Autowired
	private FootprintService footprintService;
	@Autowired
	private UserContextUtil userContextUtil;
	@Autowired
	private SearchService  searchService;
	@Autowired
	private ConfigService confingService;
	@Autowired
	private  ItemBrandMapper itemBrandMapper;
	
	@ApiOperation(value = "商品详情")
	@GetMapping(value = "/queryItemDetail")
	public ApiResult<ItemDetailVo> queryItemDetail(@RequestHeader("token") String token,String itemNo){
		Integer userId=userContextUtil.tokenToUserId(token);
		ItemDetailVo vo=itemService.queryItemDetail(userId,itemNo);
		footprintService.addFootprint(userId, itemNo);
		return ApiResult.ok(vo);
	}

	@ApiOperation(value = "查询团购商品详情")
	@GetMapping(value = "/queryPromotionItemDetail")
	public ApiResult<PromotionItemDetailVo> queryPromotionItemDetail(@RequestHeader("token") String token,String itemNo,Integer promotionId){
		Integer userId=userContextUtil.tokenToUserId(token);
		PromotionItemDetailVo vo=itemService.queryPromotionItemDetail(userId,itemNo,promotionId);
		footprintService.addFootprint(userId, itemNo);
		return ApiResult.ok(vo);
	}

	@ApiOperation(value = "根据商品名称模糊查询商品列表")
	@GetMapping(value = "/listItemsByItemName")
	public ApiResult<List<ItemVo>> listItemsByItemName(String itemName){
		List<ItemVo> list=itemService.listItemsByItemName(itemName);
		return ApiResult.ok(list);
	}

	@ApiOperation(value = "商品分页列表")
	@GetMapping(value = "/page")
	public ApiResult<List<ItemVo>> list(@RequestHeader("token") String token,ProductQueryParam productQueryParam) {
		// 是否进行搜索记录新增标识
		// 如果searchValue 不等于空 且为第一页 且有查询出记录 则进行搜索记录新增
		boolean  searchTag=false;
		Integer userId=userContextUtil.tokenToUserId(token);
		QueryWrapper<Item> wrapper = new QueryWrapper<>();
		if(Objects.nonNull(productQueryParam.getItemBrandId())) {
			wrapper.eq("item_brand_id", productQueryParam.getItemBrandId());
		}
		if(Objects.nonNull(productQueryParam.getItemCategoryId())) {
			wrapper.eq("item_category_id", productQueryParam.getItemCategoryId());
		}
		if(Objects.nonNull(productQueryParam.getSearchValue())) {
			//  品名
			wrapper.like("item_name", productQueryParam.getSearchValue());
			if(productQueryParam.getPageNum()==1) {
				searchTag=true;
			}
		}
		if(Objects.nonNull(productQueryParam.getSortType())) {
			// 排序方式  1：价格升序 2 价格降序  3 有效期升序 4 有效期降序
			switch(productQueryParam.getSortType()) {
				case  1:
					wrapper.orderByAsc("platform_price");
					break;
				case  2:
					wrapper.orderByDesc("platform_price");
					break;
				case  3:
					wrapper.orderByAsc("effective_date");
					break;
				case  4:
					wrapper.orderByDesc("effective_date");
					break;
			}
		}
		Page<Item> page=new Page<Item>();
		page.setCurrent(productQueryParam.getPageNum());
		page.setSize(productQueryParam.getPageSize());
		Page<ItemVo> itemPage = itemService.page(page, wrapper);
		if(searchTag && itemPage.getSize()>0) {
			// 新增查询记录
			searchService.addSearch(userId, productQueryParam.getSearchValue());
		}
		return ApiResult.ok(itemPage.getRecords());
	}

	
	@ApiOperation(value = "近效期商品分页列表")
	@GetMapping(value = "/nearTermItem")
	public ApiResult<List<ItemVo>> nearTermItem(@RequestHeader("token") String token,ProductQueryParam productQueryParam) {
		Integer userId=userContextUtil.tokenToUserId(token);
		Config config=confingService.queryByCode(Constant.NEAR_TERM_ITEM);
		QueryWrapper<Item> wrapper = new QueryWrapper<>();
		// 近效期商品 查询进半年的商品
		wrapper.lt("effective_date",  DateUtils.addDay(new Date(), Integer.parseInt(config.getValue())));
		if(Objects.nonNull(productQueryParam.getItemBrandId())) {
			wrapper.eq("item_brand_id", productQueryParam.getItemBrandId());
		}
		if(Objects.nonNull(productQueryParam.getItemCategoryId())) {
			wrapper.eq("item_category_id", productQueryParam.getItemCategoryId());
		}
	    if(Objects.nonNull(productQueryParam.getSearchValue())) {
			//  品名
			wrapper.like("item_name", productQueryParam.getSearchValue());
		}
	    
	    if(Objects.nonNull(productQueryParam.getSortType())) {
			// 排序方式  1：价格升序 2 价格降序  3 有效期升序 4 有效期降序
			switch(productQueryParam.getSortType()) {
				case  1:
					wrapper.orderByAsc("platform_price");
					break;
				case  2:
					wrapper.orderByDesc("platform_price");
					break;
				case  3:
					wrapper.orderByAsc("effective_date");
					break;
				case  4:
					wrapper.orderByDesc("effective_date");
					break;
			}
		}
		Page<Item> page=new Page<Item>();
		page.setCurrent(productQueryParam.getPageNum());
		page.setSize(productQueryParam.getPageSize());
		Page<ItemVo> itemPage = itemService.page(page, wrapper);
		return ApiResult.ok(itemPage.getRecords());
	}


	@ApiOperation(value = "品牌商品分页列表")
	@GetMapping(value = "/brandItem")
	public ApiResult<List<ItemVo>> brandItem(@RequestHeader("token") String token,ProductQueryParam productQueryParam) {
		Integer userId=userContextUtil.tokenToUserId(token);
		QueryWrapper<Item> wrapper = new QueryWrapper<>();
		// 将其作为品牌参数查询
		List<Integer> brandIds=new ArrayList<>();
		if(Objects.nonNull(productQueryParam.getSearchValue())) {
			// 品名
			List<ItemBrand> brandList=itemBrandMapper.selectList(new QueryWrapper<ItemBrand>().like("ibrand_name", productQueryParam.getSearchValue()));
			brandIds=brandList.stream().map(bean->bean.getId()).collect(Collectors.toList());
			if(brandIds.size()==0) {
				return ApiResult.ok(new ArrayList<ItemVo>());
			}
		}
		wrapper.in(brandIds.size()>0,"item_brand_id", brandIds);
		if(Objects.nonNull(productQueryParam.getSortType())) {
			// 排序方式  1：价格升序 2 价格降序  3 有效期升序 4 有效期降序
			switch(productQueryParam.getSortType()) {
				case  1:
					wrapper.orderByAsc("platform_price");
					break;
				case  2:
					wrapper.orderByDesc("platform_price");
					break;
				case  3:
					wrapper.orderByAsc("effective_date");
					break;
				case  4:
					wrapper.orderByDesc("effective_date");
					break;
			}
		}
		Page<Item> page=new Page<Item>();
		page.setCurrent(productQueryParam.getPageNum());
		page.setSize(productQueryParam.getPageSize());
		Page<ItemVo> itemPage = itemService.page(page, wrapper);
		return ApiResult.ok(itemPage.getRecords());
	}

}
