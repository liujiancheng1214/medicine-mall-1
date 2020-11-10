package cn.jdcloud.medicine.mall.api.biz.admin.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandDetailVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBrandService;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName ItemBrandController
 * @Author wuzhiyong
 * @Date 2020/8/13 9:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/cms/brand")
@Api(tags = "品牌管理接口")
public class ItemBrandController {

    @Resource
    ItemBrandService itemBrandService;

    @ApiOperation(value = "品牌数据列表")
    @GetMapping(value = "/page")
    public ApiResult list(Page page,String searchValue) {
        Page itemBrandPage = itemBrandService.pageList(page,searchValue);
        return ApiResult.ok(itemBrandPage);
    }

    @ApiOperation(value = "查询子类别")
    @GetMapping(value = "/sub")
    public ApiResult subNode(Integer parentId, HttpServletRequest request) {
        return ApiResult.ok(itemBrandService.subNode(parentId));
    }

    @ApiOperation(value = "添加品牌")
    @PostMapping(value = "/add")
    public ApiResult add(@RequestBody ItemBrand itemBrand, HttpServletRequest request) {

        itemBrandService.saveBrand(itemBrand,request);
        return ApiResult.ok();
    }

    @ApiOperation(value = "品牌详情")
    @GetMapping(value = "/detail")
    public ApiResult detail( Integer id, HttpServletRequest request) {
        ItemBrandDetailVo itemBrandDetailVo = itemBrandService.detail(id);
        return ApiResult.ok(itemBrandDetailVo);
    }

    @ApiOperation(value = "更新品牌")
    @PostMapping(value = "/update")
    public ApiResult update(@RequestBody ItemBrand itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setUpdator(session.getUserId());
        itemBrandService.updateById(itemBrand);
        return ApiResult.ok();
    }

    @ApiOperation(value = "删除品牌")
    @PostMapping(value = "/delete")
    public ApiResult delete(@RequestBody ItemBrand itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setUpdator(session.getUserId());
        itemBrandService.delete(itemBrand);
        return ApiResult.ok();
    }
    @ApiOperation(value = "取消删除品牌")
    @PostMapping(value = "/undelete")
    public ApiResult undelete(@RequestBody ItemBrand itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setUpdator(session.getUserId());
        itemBrandService.undelete(itemBrand);
        return ApiResult.ok();
    }
}
