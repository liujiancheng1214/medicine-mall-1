package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.FootprintService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.FootprintVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/footprint")
@Api(tags = "我的足迹接口")
public class FootprintRest {

    @Autowired
    private FootprintService footprintService;

	@Autowired
	private UserContextUtil userContextUtil;
    @ApiOperation(value = "分页查询我的足迹列表")
    @GetMapping(value = "/listFootprint")
    public ApiResult<List<FootprintVo>> listFootprint(@RequestHeader("token") String token,int pageNum, int pageSize) {
    	Integer userId=userContextUtil.tokenToUserId(token);
        List<FootprintVo> list = footprintService.listFootprint(userId, pageNum, pageSize);
        return ApiResult.ok(list);
    }

    @ApiOperation(value = "删除我的足迹,返回删除记录数")
    @GetMapping(value = "/deleteFootprint")
    public ApiResult<Integer> deleteFootprint(@RequestHeader("token") String token,String ids) {
    	Integer userId=userContextUtil.tokenToUserId(token);
        int i = footprintService.deleteFootprint(userId, Arrays.asList(ids.split(",")));
        return ApiResult.ok(i);
    }
}
