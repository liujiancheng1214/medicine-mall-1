package cn.jdcloud.medicine.mall.api.biz.user.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserLevelService;
import cn.jdcloud.medicine.mall.domain.user.UserLevel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenQF
 * @desc 后台用户等级相关
 * @date 2020/8/14 0014 13:49
 */
@RestController
@Api(tags = "用户等级相关接口")
@RequestMapping("/cms/level")
public class UserLevelController extends BaseController {

    @Resource
    UserLevelService userLevelService;

    @ApiOperation(value = "查询等级信息(分页)")
    @GetMapping(value = "/list")
    public ApiResult listLevel(Page page) {
        page = userLevelService.listLevel(page);
        return ApiResult.ok(page);
    }

    @ApiOperation(value = "查询所有等级信息")
    @GetMapping(value = "/listAll")
    public ApiResult listAllLevel() {
        return ApiResult.ok(userLevelService.listAllLevel());
    }

    @ApiOperation(value = "新增等级信息")
    @PostMapping(value = "/save")
    public ApiResult saveUserLevel(@RequestBody UserLevel userLevel) {
        userLevelService.saveUserLevel(userLevel);
        return ApiResult.ok();
    }

    @ApiOperation(value = "删除等级信息")
    @PostMapping(value = "/delete")
    public ApiResult deleteUserLevel(@RequestBody UserLevel userLevel) {
        userLevelService.deleteUserLevel(userLevel.getId());
        return ApiResult.ok();
    }

    @ApiOperation(value = "更新等级信息")
    @PostMapping(value = "/update")
    public ApiResult updateUserLevel(@RequestBody UserLevel userLevel) {
        userLevelService.updateUserLevel(userLevel);
        return ApiResult.ok();
    }

}
