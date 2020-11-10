package cn.jdcloud.medicine.mall.api.biz.promotion.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupService;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author chenQF
 * @desc 团购模板
 * @date 2020/8/22 0022 14:25
 */
@RestController
@RequestMapping("/cms/promotion/group")
@Api(tags = "拼团模板接口")
public class PromotionGroupController {

    @Resource
    PromotionGroupService promotionGroupService;

    @ApiOperation(value = "查询模板列表")
    @GetMapping(value = "/list")
    public ApiResult listGroup(Page page) {
        page =  promotionGroupService.listGroup(page);
        return ApiResult.ok(page);
    }

    @ApiOperation(value = "添加/修改模板列表")
    @PostMapping(value = "/save")
    public ApiResult saveOrUpdate(@RequestBody PromotionGroup promotionGroup) {
        if(promotionGroup.getId() == 0){
            promotionGroup.setUpdateTime(new Date());
            promotionGroup.setCreateTime(new Date());
        }
        promotionGroupService.saveOrUpdate(promotionGroup);
        return ApiResult.ok();
    }

    @ApiOperation(value = "删除模板列表")
    @PostMapping(value = "/delete")
    public ApiResult deleteGroup(@RequestBody PromotionGroup promotionGroup) {
        promotionGroupService.deleteGroup(promotionGroup.getId());
        return ApiResult.ok();
    }
}
