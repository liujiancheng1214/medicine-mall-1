package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.FootprintService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.FootprintVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/footprint")
@Api(tags = "我的足迹接口")
public class FootprintRest {

    @Autowired
    private FootprintService footprintService;


    @ApiOperation(value = "分页查询我的足迹列表")
    @GetMapping(value = "/listFootprint")
    public ApiResult<List<FootprintVo>> listFootprint(int pageNum, int pageSize) {
        Integer userId = 1;
        List<FootprintVo> list = footprintService.listFootprint(userId, pageNum, pageSize);
        return ApiResult.ok(list);
    }

    @ApiOperation(value = "删除我的足迹,返回删除记录数")
    @GetMapping(value = "/deleteFootprint")
    public ApiResult<Integer> deleteFootprint(String ids) {
        Integer userId = 1;
        List<Integer> list = new ArrayList<>();
        String[] str = ids.split(",");
        for (String s : str) {
            list.add(Integer.parseInt(s));
        }
        int i = footprintService.deleteFootprint(userId, list);
        return ApiResult.ok(i);
    }
}
