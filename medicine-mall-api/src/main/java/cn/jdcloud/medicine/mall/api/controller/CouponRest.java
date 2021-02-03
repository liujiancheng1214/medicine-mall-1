package cn.jdcloud.medicine.mall.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/coupon")
@Api(tags = "优惠券模板")
public class CouponRest {
	@Autowired
	private CouponService  couponService;
	
	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "查询可领优惠券列表")
	@GetMapping(value = "/listCoupon")
	@ApiImplicitParams(
			@ApiImplicitParam(name="limitType", dataType="Byte",value="限制类型: 0 不受限制   1 商品限制  2 品牌限制 3 类别限制 ")
			)
	public ApiResult<Page<CouponVo>> listCoupon(@RequestHeader("token") String token,
			Byte limitType,int pageNum,int pageSize) {
		Integer userId=userContextUtil.tokenToUserId(token);
		Page<CouponVo>  page=couponService.listCoupon(pageNum, pageSize, userId, limitType);
		return ApiResult.ok(page);
	}




}
