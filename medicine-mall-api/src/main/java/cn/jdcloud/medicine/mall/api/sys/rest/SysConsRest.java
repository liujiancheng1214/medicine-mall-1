package cn.jdcloud.medicine.mall.api.sys.rest;

import cn.jdcloud.medicine.mall.api.sys.service.SysConsService;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.sys.dto.SysConsDto;
import cn.jdcloud.medicine.mall.api.sys.service.impl.SysConsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cms/sys/cons")
@Api(tags = "常量维护")
public class SysConsRest {

    @Resource
    SysConsService sysConsService;

    @GetMapping(value = "/modules/list")
    @ApiOperation("常量维护->列举常量")
    public ApiResult listModules(){
        List<SysConsServiceImpl.ConsModuleVo> consModuleVoList = sysConsService.listModules();
        return  ApiResult.ok(consModuleVoList);
    }

    @PostMapping(value = "/save")
    @ApiOperation("常量维护->新增常量")
    public ApiResult saveSysCons(@RequestBody SysConsDto sysConsDto){
         sysConsService.saveSysCons(sysConsDto);
        return  ApiResult.ok();
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation("常量维护->删除常量")
    public ApiResult deleteSysCons( SysConsDto sysConsDto){
        sysConsService.deleteSysCons(sysConsDto);
        return  ApiResult.ok();
    }
}
