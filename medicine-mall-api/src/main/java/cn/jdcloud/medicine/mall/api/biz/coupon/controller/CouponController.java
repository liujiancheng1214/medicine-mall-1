package cn.jdcloud.medicine.mall.api.biz.coupon.controller;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponRecordService;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponService;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponDto;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author chenQF
 * @desc 优惠券相关接口
 * @date 2020/8/20 0020 18:31
 */
@RestController
@RequestMapping("/cms/coupon")
@Api(tags = "优惠券相关接口")
public class CouponController {

    @Resource
    CouponService couponService;

    @Resource
    CouponRecordService couponRecordService;

    @ApiOperation(value = "优惠券列表")
    @GetMapping(value = "/list")
    public ApiResult couponList(Page page, String type) {
        page = couponService.listCoupon(page, type);
        return ApiResult.ok(page);
    }

    @ApiOperation(value = "用户领取记录列表")
    @GetMapping(value = "/listRecord")
    public ApiResult listRecord(Page page, CouponDto couponDto) {
        page = couponRecordService.listCouponRecord(page, couponDto);
        return ApiResult.ok(page);
    }

    @ApiOperation(value = "发放优惠券")
    @PostMapping(value = "/addRecord")
    public ApiResult addRecord(@RequestBody CouponRecordDto dto) {
        couponRecordService.add(dto);
        return ApiResult.ok();
    }

    @ApiOperation(value = "新增/修改优惠券信息")
    @PostMapping(value = "/save")
    public ApiResult saveUser(@RequestBody Coupon coupon, HttpServletRequest request) {
        try {
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            Integer userId = userSession.getUserId();
            if (coupon.getId() == 0) {
                coupon.setCreator(userId);
                coupon.setUpdator(userId);
                coupon.setCreateTime(new Date());
                coupon.setUpdateTime(new Date());
            }else{
                coupon.setUpdator(userId);
            }
            boolean saveOrUpdate = couponService.saveOrUpdate(coupon);
            return ApiResult.ok(saveOrUpdate);
        } catch (NullPointerException e) {
            throw new ApiException(1000, "用户未登录");
        }
    }

    @ApiOperation(value = "删除优惠券")
    @PostMapping(value = "/delete")
    public ApiResult deleteCoupon(@RequestBody Coupon coupon) {
        couponService.delete(coupon.getId());
        return ApiResult.ok();
    }
}
