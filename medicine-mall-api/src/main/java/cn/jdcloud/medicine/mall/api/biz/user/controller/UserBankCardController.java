package cn.jdcloud.medicine.mall.api.biz.user.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserBankCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenQF
 * @desc 客户银行卡相关接口
 * @date 2020/8/26 0014 09:53
 */
@RestController
@RequestMapping("/cms/user/bankCard")
@Api(tags = "客户银行卡相关接口")
public class UserBankCardController extends BaseController {

    @Resource
    UserBankCardService userBankCardService;

    @ApiOperation(value = "根据userId查询客户卡信息")
    @GetMapping(value = "/list")
    public ApiResult getOne(Integer userId) {
        return ApiResult.ok(userBankCardService.getByUserId(userId));
    }

}
