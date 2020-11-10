package cn.jdcloud.medicine.mall.api.sys.rest;

import cn.jdcloud.framework.common.vo.VantSelector;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.sys.service.RegionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公共系统类接口
 */
@RestController
@RequestMapping(value = "/pub/region")
public class SysRegionRest extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(SysRegionRest.class);

    @Resource
    private RegionService regionService;

    /**
     * 查询商品种类
     */
    @ApiOperation("查询地区信息")
    @GetMapping(value = "/regionList")
    public ApiResult regionList() throws Exception{
        List<VantSelector> list  = regionService.vantSelectList();
        return ApiResult.ok(list);
    }

}
