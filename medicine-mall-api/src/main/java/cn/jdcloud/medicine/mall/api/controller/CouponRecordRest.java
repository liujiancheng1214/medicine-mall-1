package cn.jdcloud.medicine.mall.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponRecordService;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.ItemNumVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.UserCouponVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/couponRecord")
@Api(tags = "优惠券相关接口")
public class CouponRecordRest {

    @Autowired
    private CouponRecordService couponRecordService;
    @Autowired
    private UserContextUtil userContextUtil;

    @ApiOperation(value = "我的优惠券列表")
    @GetMapping(value = "/listCouponRecord")
    public ApiResult<UserCouponVo> listCouponRecord(@RequestHeader("token") String token) {
        Integer userId = userContextUtil.tokenToUserId(token);
        UserCouponVo userCouponVo = couponRecordService.listCouponRecordByUserIdAndStatus(userId, null);
        return ApiResult.ok(userCouponVo);
    }


    @ApiOperation(value = "查询可用优惠券列表")
    @PostMapping(value = "/listCouponRecordWithItemNo")
    public ApiResult<CouponVo> listCouponRecordWithItemNo(@RequestHeader("token") String token, @RequestBody List<ItemNumVo> itemNos) {
        Integer userId = userContextUtil.tokenToUserId(token);
        List<CouponVo> list = couponRecordService.listCouponRecordByUserIdAndItemNos(userId, itemNos);
        return ApiResult.ok(list);
    }


    @ApiOperation(value = "优惠券领取,返回1  领取成功")
    @GetMapping(value = "/addCoupon")
    public ApiResult<String> addCoupon(@RequestHeader("token") String token, String couponId) {
        Integer userId = userContextUtil.tokenToUserId(token);
        CouponRecordDto  dto=new CouponRecordDto();
        dto.setCouponId(couponId);
        dto.setSendType("0");
        dto.setUserIds(Arrays.asList(userId));
        couponRecordService.add(dto);
        return ApiResult.ok("1");
    }
    
}
