package cn.jdcloud.medicine.mall.api.biz.promotion.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionInfoService;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfo;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoDto;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInitiateDto;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author chenQF
 * @desc 拼团记录
 * @date 2020/8/24 0024 13:52
 */
@RestController
@RequestMapping("/cms/group")
@Api(tags = "拼团记录接口")
public class GroupInfoController {

    @Resource
    GroupInfoService groupInfoService;
    @Resource
    PromotionInfoService promotionInfoService;

    @ApiOperation(value = "查询拼团记录")
    @GetMapping(value = "/list")
    public ApiResult listGroupInfo(Page page, GroupInfoDto dto) {
        page =  groupInfoService.listGroupInfo(page, dto);
        return ApiResult.ok(page);
    }

    @ApiOperation(value = "后台发起拼团")
    @PostMapping(value = "/initiate")
    public ApiResult initiate(@RequestBody GroupInitiateDto dto, HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        Integer userId = userSession.getUserId();
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setPromotionId(dto.getPromotionId());
        groupInfo.setPlushUserId(userId);
        groupInfo.setPlushType(1);
        groupInfo.setCreateTime(new Date());
        PromotionInfo promotionInfo = promotionInfoService.getById(dto.getPromotionId());
        groupInfo.setEndTime(promotionInfo.getEndTime());
        groupInfoService.save(groupInfo);
        return ApiResult.ok();
    }

}
