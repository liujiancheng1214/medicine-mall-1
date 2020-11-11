package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PromotionItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.FootprintService;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.api.params.ProductQueryParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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





	@ApiOperation(value = "商品分页列表")
	@GetMapping(value = "/page")
	public ApiResult<List<ItemVo>> list(ProductQueryParam productQueryParam) {
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


}
