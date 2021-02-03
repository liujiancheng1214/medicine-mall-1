package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.vo.IndexPromotionItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promotion")
@Api(tags = "团购接口")
public class PromotionRest {
    @Autowired
    private PromotionGroupItemService promotionGroupItemService;
    @Autowired
	private UserContextUtil userContextUtil;

    @ApiOperation(value = "查询活动列表列表")
    @GetMapping(value = "/listPromotionItems")
    public ApiResult<List<IndexPromotionItemVo>> listPromotionItems(@RequestHeader("token") String token,String searchValue,int pageNum,int pageSize,int type) {
    	Integer userId=userContextUtil.tokenToUserId(token);
    	List<IndexPromotionItemVo> list=promotionGroupItemService.listPromotionItemVo(userId, pageNum, pageSize, searchValue, type);
        return ApiResult.ok(list);
    }


    



}
