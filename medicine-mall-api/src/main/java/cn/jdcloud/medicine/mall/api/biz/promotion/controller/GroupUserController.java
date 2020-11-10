package cn.jdcloud.medicine.mall.api.biz.promotion.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenQF
 * @desc 拼团参与参与记录
 * @date 2020/8/24 0024 15:48
 */
@RestController
@RequestMapping("/cms/group/user")
@Api(tags = "拼团参与记录接口")
public class GroupUserController {

    @Resource
    GroupUserService groupUserService;

    @ApiOperation(value = "查询拼团参与记录")
    @GetMapping(value = "/list")
    public ApiResult listGroupUser(Page page, String groupId) {
        page =  groupUserService.listGroupUser(page, groupId);
        return ApiResult.ok(page);
    }

}
