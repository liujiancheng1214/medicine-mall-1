package cn.jdcloud.medicine.mall.api.biz.admin.controller;

import cn.jdcloud.medicine.mall.api.biz.admin.dto.AdminDto;
import cn.jdcloud.medicine.mall.api.biz.admin.service.AdminService;
import cn.jdcloud.medicine.mall.api.biz.admin.service.RoleService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminRolesVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminVo;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author qun.xu
 * @desc   后台用户相关
 * @date  2018/9/12 14:16
 */
@RestController
@RequestMapping("/cms/admin")
@Api(tags = "后台用户接口")
public class AdminController extends BaseController {

    @Resource AdminService adminService;
    @Resource
    RoleService roleService;

    @ApiOperation(value = "管理员数据列表")
    @GetMapping(value = "/list")
    public ApiResult list(Page page,String search) {
        page =  adminService.listAdmin(page,search);
        return ApiResult.ok(page);
    }

    @ApiOperation(value = "冻结用户")
    @PostMapping(value = "/freeze")
    public ApiResult freeze(@RequestBody AdminDto adminDto) {
        adminService.freezeAdmin(adminDto);
        return ApiResult.ok();
    }
    @ApiOperation(value = "解冻用户")
    @PostMapping(value = "/unfreeze")
    public ApiResult unfreeze(@RequestBody AdminDto adminDto) {
        adminService.unfreezeAdmin(adminDto);
        return ApiResult.ok();
    }
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/save")
    public ApiResult saveAdmin(@RequestBody AdminDto adminDto) {
        adminService.saveAdmin(adminDto);
        return ApiResult.ok();
    }
    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/restPassword")
    public ApiResult restPassword(@RequestBody AdminDto adminDto) {
        adminService.restPassword(adminDto);
        return ApiResult.ok();
    }
    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/password/update")
    public ApiResult updatePassword(@RequestBody AdminDto adminDto) {
        adminService.updatePassword(adminDto);
        return ApiResult.ok();
    }

    @ApiOperation(value = "用户详细信息")
    @GetMapping(value = "/info")
    public ApiResult info(Integer adminId) {
        AdminVo adminVo = adminService.getAdminInfo(adminId);
        return ApiResult.ok(adminVo);
    }

    @ApiOperation(value = "获取用户角色信息")
    @GetMapping(value = "/role")
    public ApiResult getAdminRoles(Integer adminId) {
        AdminRolesVo adminRolesVo = adminService.getAdminRoles(adminId);
        return ApiResult.ok(adminRolesVo);
    }

    @ApiOperation(value = "保存用户角色")
    @PostMapping(value = "/role/save")
    public ApiResult saveAdminRoles(@RequestBody AdminDto adminDto) {
        adminService.saveAdminRoles(adminDto);
        return ApiResult.ok();
    }
}