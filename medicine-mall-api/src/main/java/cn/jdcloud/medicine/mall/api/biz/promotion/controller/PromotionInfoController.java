package cn.jdcloud.medicine.mall.api.biz.promotion.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.dto.PromotionDto;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionItemVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionVo;
import cn.jdcloud.medicine.mall.api.common.exception.CustomException;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroup;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/cms/promotion")
@Api(tags = "后台促销活动接口")
public class PromotionInfoController {
    @Resource
    ItemService itemService;
    @Resource
    PromotionGroupService promotionGroupService;
    @Resource
    PromotionGroupItemService promotionGroupItemService;
    @Resource
    PromotionInfoService promotionInfoService;

    @ApiOperation(value = "促销活动分页列表")
    @GetMapping(value = "/page")
    public ApiResult list(Page<PromotionInfo> page, String conditions) {
        Map<String, String> map = new Gson().fromJson(conditions, Map.class);
        String tab = map.get("tab");
        QueryWrapper<PromotionInfo> wrapper = new QueryWrapper<PromotionInfo>()
                .eq("is_deleted", 0)
                .eq("0".equals(tab), "audit_status", 2);
        if (!"0".equals(tab)) {
            Date nowTime = new Date();
            wrapper.eq("audit_status", 1)
                    .gt("1".equals(tab), "begin_time", nowTime)
                    .lt("2".equals(tab), "begin_time", nowTime)
                    .gt("2".equals(tab), "end_time", nowTime)
                    .lt("3".equals(tab), "end_time", nowTime);
        }
        String searchValue = map.get("searchValue");
        wrapper.and(StringUtils.isNotBlank(searchValue), query -> query.like("promotion_id", searchValue)
                .or().like("promotion_name", searchValue)
                .or().like("share_title", searchValue)
        );
        Page<PromotionVo> promotionPage = promotionInfoService.page(page, wrapper);
        return ApiResult.ok(promotionPage);
    }

    @ApiOperation(value = "促销活动规则模板选择")
    @GetMapping(value = "/rule")
    public ApiResult rule(String query, Integer type) {
        if (type == 1 && StringUtils.isNotBlank(query)) {
            List<PromotionGroup> list = promotionGroupService.list(
                    new QueryWrapper<PromotionGroup>()
                            .like("group_name", query).or()
                            .like("id", query).or()
                            .like("rule", query));
            return ApiResult.ok(list);
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "获取活动规则模板和商品")
    @GetMapping(value = "/getRuleAndItem")
    public ApiResult getPromotionItems(Integer id,Integer ruleId,Integer type) {
        if(type==1){
            HashMap<String, Object> map = new HashMap<>();
            PromotionGroup group = promotionGroupService.getById(ruleId);
            ArrayList<PromotionItemVo> itemList = new ArrayList<>();
            promotionGroupItemService.PromotionGroupItemInfo(id).forEach(item -> itemList.add(new PromotionItemVo(item)));
            map.put("rule",group);
            map.put("items",itemList);
            return ApiResult.ok(map);
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "保存活动")
    @PostMapping(value = "/save")
    public ApiResult save(@RequestBody PromotionDto promotionDto, HttpServletRequest request) {
        try {
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            Integer userId = userSession.getUserId();
            boolean save = promotionInfoService.save(promotionDto,userId);
            return ApiResult.ok(save);
        } catch (CustomException e) {
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "删除活动")
    @PostMapping(value = "/delete")
    public ApiResult delete(@RequestParam("promotionIds") String promotionIds) {
        List<Integer> ids = new Gson().fromJson(promotionIds, List.class);
        if (CollectionUtils.isNotEmpty(ids)) {
            PromotionInfo promotionInfo = new PromotionInfo();
            promotionInfo.setIsDeleted((byte)1);
            promotionInfoService.update(promotionInfo, new QueryWrapper<PromotionInfo>().in("promotion_id", ids));
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "审核活动")
    @PostMapping(value = "/approve")
    public ApiResult approve(@RequestParam("id") Integer id, HttpServletRequest request) {
        if (id!=null) {
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            Integer userId = userSession.getUserId();
            PromotionInfo promotionInfo = new PromotionInfo();
            promotionInfo.setAuditStatus((byte)1);
            promotionInfo.setAuditor(userId);
            promotionInfoService.update(promotionInfo, new QueryWrapper<PromotionInfo>().eq("promotion_id", id));
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "取消活动")
    @PostMapping(value = "/cancel")
    public ApiResult cancel(@RequestParam("id") Integer id) {
        if (id!=null) {
            PromotionInfo promotionInfo = new PromotionInfo();
            promotionInfo.setAuditStatus((byte)2);
            promotionInfoService.update(promotionInfo, new QueryWrapper<PromotionInfo>().eq("promotion_id", id));
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "查找正在进行的活动")
    @GetMapping(value = "/ongoingActivities")
    public ApiResult ongoingActivities() {
        Date date = new Date();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(date);
        QueryWrapper<PromotionInfo> wrapper = new QueryWrapper<PromotionInfo>();
        		wrapper.eq("audit_status",1).eq("is_deleted",0);
        		wrapper.and(wrapper2 -> wrapper2.gt("end_time", now).or().eq("end_time", now));
        		wrapper.and(wrapper2 -> wrapper2.lt("begin_time", now).or().eq("begin_time", now));
        List<PromotionInfo> list = promotionInfoService.list(wrapper);
        return ApiResult.ok(list);
    }
}
