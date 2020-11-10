package cn.jdcloud.medicine.mall.api.biz.admin.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.code.RoleCode;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.RoleDto;
import cn.jdcloud.medicine.mall.api.biz.admin.service.RolePermService;
import cn.jdcloud.medicine.mall.api.biz.admin.service.RoleService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import cn.jdcloud.medicine.mall.domain.admin.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qun.xu
 * @desc   后台用户相关
 */
@RestController
@RequestMapping("/cms/sys/role")
@Api(tags = "后台用户接口")
public class RoleController extends BaseController {

    @Resource
    RoleService roleService;

    @Resource
    RolePermService rolePermService;

    @ApiOperation(value = "角色列表")
    @GetMapping(value = "/page")
    public ApiResult list(Page<Role> page, String searchValue) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(searchValue)){
            wrapper.like("role_name",searchValue);
        }
        Page<RoleVo> rolePage = roleService.page(page,wrapper);
        return ApiResult.ok(rolePage);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/save")
    public ApiResult saveOrUpdateRole(@RequestBody RoleDto roleDto) throws Exception{
        Integer id = roleDto.getId();
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().eq("role_name",roleDto.getRoleName());
        if(id!=null){
            queryWrapper.ne("id",id);
        }
        List<Role> roles = roleService.list(queryWrapper);
        if(roles.size()!=0){
            return ApiResult.error(RoleCode.ROLE_NAME_EXIST);
        }
        roleService.saveOrUpdateRole(roleDto);
        return ApiResult.ok();
    }

    @GetMapping("/perms")
    public ApiResult loadRolePerms(Integer roleId) {
        List<RolePermission> rolePerms = rolePermService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        List<Integer> list = rolePerms.stream().map(rolePerm -> rolePerm.getPermissionId()).collect(Collectors.toList());
        return ApiResult.ok(list);
    }

}