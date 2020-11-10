package cn.jdcloud.medicine.mall.api.biz.user.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.user.utils.EncryptUtils;
import cn.jdcloud.medicine.mall.domain.user.UserDto;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.domain.user.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author chenQF
 * @desc 后台用户相关
 * @date 2020/8/14 0014 13:49
 */
@RestController
@RequestMapping("/cms/user")
@Api(tags = "用户相关接口")
public class UserController extends BaseController {

    @Resource
    UserService userService;

    @ApiOperation(value = "用户数据列表")
    @GetMapping(value = "/list")
    public ApiResult userList(Page page, UserDto userDto) {
        return ApiResult.ok(userService.listUser(page,userDto));
    }

    @ApiOperation(value = "用户下拉框")
    @GetMapping(value = "/listSelect")
    public ApiResult getAllUser(String userName) {
        return ApiResult.ok(userService.getAllUser(userName));
    }

    /**
     * @param userId 用户的id
     */
    @ApiOperation(value = "查询用户详细信息")
    @GetMapping(value = "/info")
    public ApiResult getUser(Integer userId) {
        User user =  userService.getUser(userId);
        return ApiResult.ok(user);
    }

    @ApiOperation(value = "地址选择下拉列表")
    @GetMapping(value = "/region")
    public ApiResult getRegion() {
        return ApiResult.ok(userService.getRegion());
    }

    @ApiOperation(value = "新增/修改用户信息")
    @PostMapping(value = "/save")
    public ApiResult saveUser(@RequestBody User user) {
        if (user.getId() == null) {
            String salt = StringUtils.randomUUID();
            String password = EncryptUtils.encryptPassword(salt, "888888");
            user.setSalt(salt);
            user.setPassword(password);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
        }
        boolean saveOrUpdate = userService.saveOrUpdate(user);
        return ApiResult.ok(saveOrUpdate);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "/update")
    public ApiResult updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ApiResult.ok();
    }

    @ApiOperation(value = "导出用户数据到Excel")
    @GetMapping(value = "/export")
    public void exportExcel(HttpServletResponse response) throws Exception {
        userService.exportExcel(response,"0");
    }

    @ApiOperation(value = "下载用户Excel模板")
    @GetMapping(value = "/downLoad")
    public void downLoad(HttpServletResponse response) throws Exception {
        userService.exportExcel(response,"1");
    }

    /**
     * @param file 文件
     * @return map 中是格式不合格的数据
     * @throws Exception
     */
    @ApiOperation(value = "导入Excel数据到用户表")
    @PostMapping(value = "/read")
    public ApiResult readExcel(MultipartFile file) throws Exception {
        Map map = userService.readExcel(file);
        return ApiResult.ok(map);
    }

}
