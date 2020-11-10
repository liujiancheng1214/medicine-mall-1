package cn.jdcloud.medicine.mall.api.biz.admin.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemCategoryVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemCategoryService;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * @ClassName ItemCategoryController
 * @Author wuzhiyong
 * @Date 2020/8/13 9:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/cms/category")
@Api(tags = "类目管理接口")
public class ItemCategoryController {

    @Resource
    ItemCategoryService itemCategoryService;

    @ApiOperation(value = "品牌数据列表")
    @GetMapping(value = "/page")
    public ApiResult list(Page page, String searchValue) {
        return ApiResult.ok(itemCategoryService.pageList(page, searchValue));
    }

    @ApiOperation(value = "查询子类别")
    @GetMapping(value = "/sub")
    public ApiResult subNode(Integer parentId, HttpServletRequest request) {
        return ApiResult.ok(itemCategoryService.subNode(parentId));
    }
    @ApiOperation(value = "添加类别")
    @PostMapping(value = "/add")
    public ApiResult add(@RequestBody ItemCategory itemCategory, HttpServletRequest request) {
        itemCategoryService.add(itemCategory,request);
        return ApiResult.ok();
    }
    @ApiOperation(value = "更新类别")
    @PostMapping(value = "/update")
    public ApiResult update(@RequestBody ItemCategory itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setUpdator(session.getUserId());
        itemCategoryService.updateById(itemBrand);
        return ApiResult.ok();
    }
    @ApiOperation(value = "禁用类别")
    @PostMapping(value = "/delete")
    public ApiResult delete(@RequestBody ItemCategory itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setUpdator(session.getUserId());
        itemBrand.setIsDeleted(ItemCategory.DELETE);
        itemCategoryService.updateById(itemBrand);
        return ApiResult.ok();
    }
    @ApiOperation(value = "启用类别")
    @PostMapping(value = "/undelete")
    public ApiResult undelete(@RequestBody ItemCategory itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setUpdator(session.getUserId());
        itemBrand.setIsDeleted(ItemCategory.UNDELETE);
        itemCategoryService.updateById(itemBrand);
        return ApiResult.ok();
    }
}
