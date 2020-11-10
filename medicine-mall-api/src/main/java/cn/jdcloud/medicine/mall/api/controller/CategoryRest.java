package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemSubCategoryVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemCategoryService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryTreeVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = "类别相关接口")
public class CategoryRest {

    @Autowired
    private ItemCategoryService itemCategoryService;


    @ApiOperation(value = "获取类别树")
    @GetMapping(value = "/getTree")
    public ApiResult<List<CategoryTreeVo>> getTree() {
        List<CategoryTreeVo> tree = itemCategoryService.getTree();
        return ApiResult.ok(tree);
    }

    @ApiOperation(value = "获取类别下的子类型")
    @GetMapping(value = "/subNode")
    public ApiResult<List<ItemSubCategoryVo>> getTree(@RequestParam(required = true, value = "parentId") Integer parentId) {
        List<ItemSubCategoryVo> list = itemCategoryService.subNode(parentId);
        return ApiResult.ok(list);
    }


    @ApiOperation(value = "查询分类列表")
    @GetMapping(value = "/listCategory")
    public ApiResult<List<CategoryVo>> listCategory() {
        List<CategoryVo> list = itemCategoryService.listCategory();
        return ApiResult.ok(list);
    }
}
