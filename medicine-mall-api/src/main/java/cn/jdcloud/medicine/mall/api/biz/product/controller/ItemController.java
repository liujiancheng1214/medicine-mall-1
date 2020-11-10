package cn.jdcloud.medicine.mall.api.biz.product.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandVo;
import cn.jdcloud.medicine.mall.api.biz.product.dto.ItemDto;
import cn.jdcloud.medicine.mall.api.biz.product.excel.ItemExcel;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBrandService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemCategoryService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryTreeVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.common.exception.CustomException;
import cn.jdcloud.medicine.mall.api.common.utils.MyExcelUtil;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cms/item")
@Api(tags = "后台商品接口")
public class ItemController {
    @Resource
    ItemService itemService;
    @Resource
    ItemBrandService itemBrandService;
    @Resource
    ItemCategoryService itemCategoryService;
    @Resource
    ItemBatchService itemBatchService;

    @ApiOperation(value = "商品分页列表")
    @GetMapping(value = "/page")
    public ApiResult list(Page<Item> page, String conditions) {
        Map<String, String> map = new Gson().fromJson(conditions, Map.class);
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        String tab = map.get("tab");
        if ("2".equals(tab)) {
            wrapper.le("qty", 0);
        } else {
            wrapper.eq("state", tab);
        }
        wrapper.eq("is_deleted", 0);
        String searchValue = map.get("searchValue");
        wrapper.and(StringUtils.isNotBlank(searchValue), query -> query.like("item_no", searchValue)
                .or().like("item_name", searchValue)
                .or().like("approval_no", searchValue)
        );
        Page<ItemVo> itemPage = itemService.webPage(page, wrapper);
        return ApiResult.ok(itemPage);
    }

    @ApiOperation(value = "商品品牌列表")
    @GetMapping(value = "/brandList")
    public ApiResult brandList() {
        List<ItemBrand> brands = itemBrandService.list(new QueryWrapper<ItemBrand>().eq("is_deleted", 0));
        ArrayList<ItemBrandVo> brandsVo = new ArrayList<>();
        brands.forEach(brand -> brandsVo.add(new ItemBrandVo(brand)));
        return ApiResult.ok(brandsVo);
    }

    @ApiOperation(value = "商品类目树")
    @GetMapping(value = "/categoryTree")
    public ApiResult categoryTree() {
        List<CategoryTreeVo> tree = itemCategoryService.getTree();
        return ApiResult.ok(tree);
    }

    @ApiOperation(value = "保存商品")
    @PostMapping(value = "/save")
    public ApiResult save(@RequestBody ItemDto itemDto, HttpServletRequest request) {
        try {
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            Integer userId = userSession.getUserId();
            boolean save = itemService.save(itemDto, userId);
            return ApiResult.ok(save);
        } catch (CustomException e) {
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "改变商品状态")
    @PostMapping(value = "/changeState")
    public ApiResult changeState(@RequestParam("itemIds") String itemIds, @RequestParam("state") Byte state) {
        List<Integer> ids = new Gson().fromJson(itemIds, List.class);
        if (CollectionUtils.isNotEmpty(ids) && state != null) {
            Item item = new Item();
            item.setState(state);
            QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
            queryWrapper.gt(state == 0, "qty", 0).in("id", ids);
            itemService.update(item, queryWrapper);
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "商品导出")
    @GetMapping(value = "/export")
    public void export(String itemIds, Byte state, HttpServletResponse response) throws Exception {
        List<Integer> ids = new Gson().fromJson(itemIds, List.class);
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", state);
        if (CollectionUtils.isNotEmpty(ids)) {
            queryWrapper.in("id", ids);
        }
        List<ItemExcel> itemExports = itemService.exportList(queryWrapper);
        String filename = "商品详情.xls";
        OutputStream out = this.getDownloadStream(response, filename);
        new MyExcelUtil<ItemExcel>().exportExcel(out, itemExports, itemService.getItemDict(true));
    }

    @ApiOperation(value = "导入模板")
    @GetMapping(value = "/template")
    public void importTemplate(HttpServletResponse response) throws IOException {
        String filename = "商品导入模板.xls";
        Map<String, String[]> guideOptions = itemService.getGuideOptions();
        OutputStream out = this.getDownloadStream(response, filename);
        new MyExcelUtil<ItemExcel>().importTemplate(out, guideOptions, ItemExcel.class);
    }

    @ApiOperation(value = "导入商品数据")
    @PostMapping(value = "/import")
    public ApiResult importItems(MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            Integer userId = userSession.getUserId();
            List<ItemExcel> list = new MyExcelUtil<ItemExcel>().excelToEntity(file, ItemExcel.class, itemService.getItemDict(false));
            boolean save = itemService.importItems(list, userId);
            return ApiResult.ok(save);
        } catch (CustomException e) {
            return ApiResult.error(e);
        }
    }


    @ApiOperation(value = "商品选择搜索")
    @GetMapping(value = "/itemSelect")
    public ApiResult item(String query) {
        if (StringUtils.isNotBlank(query)) {
            List<Item> list = itemService.list(
                    new QueryWrapper<Item>()
                            .like("item_name", query).or()
                            .like("help_code", query).or()
                            .like("item_no", query));
            return ApiResult.ok(list);
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "获取商品批次信息")
    @GetMapping(value = "/getItemBatches")
    public ApiResult getItemBatchList(String no) {
        List<ItemBatch> list = itemBatchService.list(new QueryWrapper<ItemBatch>().eq("item_no", no));
        return ApiResult.ok(list);
    }

    private OutputStream getDownloadStream(HttpServletResponse response, String filename) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        return out;
    }

}
