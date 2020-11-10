package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBrandService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
@Api(tags = "品牌接口")
public class ItemBrandRest {

    @Autowired
    private ItemBrandService itemBrandService;


    @ApiOperation(value = "热销品牌列表")
    @GetMapping(value = "/listHotBrand")
    public ApiResult<List<ItemBrandVo>> listHotBrand() {
        return ApiResult.ok(itemBrandService.listHotBandList());
    }


    @ApiOperation(value = "分页品牌列表")
    @GetMapping(value = "/pageBrandList")
    public ApiResult<Page<ItemBrandVo>> listBrand(int pageNum,int pageSize,String searchValue) {
        Page<ItemBrandVo> page=itemBrandService.pageBrandList(pageNum, pageSize, searchValue);
        return ApiResult.ok(page);
    }



}
