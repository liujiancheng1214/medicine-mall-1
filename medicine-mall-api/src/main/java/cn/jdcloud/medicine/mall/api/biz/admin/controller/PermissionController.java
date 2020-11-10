package cn.jdcloud.medicine.mall.api.biz.admin.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.code.PermissionCode;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.PermDto;
import cn.jdcloud.medicine.mall.api.biz.admin.service.PermissionService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.PermissionTreeVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.PermissionVo;
import cn.jdcloud.medicine.mall.domain.admin.Permission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qun.xu
 * @desc 权限控制器
 * @date 2018/9/12 14:16
 */
@RestController
@RequestMapping("/cms/sys/perm")
public class PermissionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    PermissionService permissionService;

    @ApiOperation(value = "权限分页列表")
    @GetMapping(value = "/page")
    public ApiResult list(Page<Permission> page, String searchValue) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(searchValue)){
            wrapper.like("name",searchValue).or().like("value",searchValue);
        }
        Page<PermissionVo> rolePage = permissionService.page(page,wrapper);
        return ApiResult.ok(rolePage);
    }

    @ApiOperation(value = "权限树")
    @GetMapping(value = "/permTree")
    public ApiResult permTree() {
        List<PermissionTreeVo> tree = permissionService.getTree();
        return ApiResult.ok(tree);
    }

    @ApiOperation(value = "保存权限")
    @PostMapping(value = "/save")
    public ApiResult save(@RequestBody PermDto PermDto) {
        Permission permission = PermDto.createPermission();
        Integer id = permission.getId();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>().eq("value", permission.getValue());
        if (id != null) {
            permission.setId(id);
            queryWrapper.ne("id", id);
        }
        List<Permission> list = permissionService.list(queryWrapper);
        if (list.size() != 0) {
            return ApiResult.error(PermissionCode.PERM_VALUE_EXIST);
        }
        boolean save = permissionService.saveOrUpdate(permission);
        if (save) {
            return ApiResult.ok();
        } else {
            return ApiResult.errorMsg(100,"操作失败","");
        }
    }

}