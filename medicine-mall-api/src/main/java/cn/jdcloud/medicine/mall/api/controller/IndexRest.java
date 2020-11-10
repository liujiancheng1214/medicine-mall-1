package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.carousel.service.CarouselService;
import cn.jdcloud.medicine.mall.api.biz.carousel.vo.CarouselVo;
import cn.jdcloud.medicine.mall.api.biz.notice.service.NoticeService;
import cn.jdcloud.medicine.mall.api.biz.notice.vo.NoticeVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
@Api(tags = "首页相关接口")
public class IndexRest {


	@Autowired
	private NoticeService noticeService;

	@Autowired
	private CarouselService carouselService;
	@Autowired
	private ItemService itemService;


	@ApiOperation(value = "查询首页公告")
	@GetMapping(value = "/queryLastNotice")
	public ApiResult<NoticeVo> queryLastNotice(){
		NoticeVo notice=noticeService.queryLastNotice();
		return ApiResult.ok(notice);
	}

	@ApiOperation(value = "查询首页轮播")
	@GetMapping(value = "/queryCarousel")
	public ApiResult<CarouselVo> queryCarousel(){
		List<CarouselVo> list=carouselService.listCarousel();
		return ApiResult.ok(list);
	}


	@ApiOperation(value = "查询首页推荐商品")
	@GetMapping(value = "/queryItem")
	public ApiResult<List<ItemVo>> queryItem(){
		List<ItemVo> list=itemService.listItemRecommend("1");
		return ApiResult.ok(list);
	}

}
