package cn.jdcloud.medicine.mall.api.biz.user.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserDescService;
import cn.jdcloud.medicine.mall.domain.user.UserDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenQF
 * @desc 用户备注相关
 * @date 2020/8/27 0027 10:44
 */
@RestController
@RequestMapping("/cms/user/desc")
@Api(tags = "用户备注接口")
public class UserDescController extends BaseController {

    @Resource
    UserDescService userDescService;

    @ApiOperation(value = "查询用户备注")
    @GetMapping(value = "/list")
    public ApiResult listUserDesc(Integer userId) {
        return ApiResult.ok(userDescService.getAllUserDesc(userId));
    }

    @ApiOperation(value = "添加用户备注")
    @PostMapping(value = "/addDesc")
    public ApiResult addDesc(@RequestBody UserDesc userDesc) {
        userDescService.add(userDesc);
        return ApiResult.ok();
    }

}
