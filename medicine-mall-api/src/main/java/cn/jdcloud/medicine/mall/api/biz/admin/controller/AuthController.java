package cn.jdcloud.medicine.mall.api.biz.admin.controller;


import cn.jdcloud.medicine.mall.api.biz.admin.dto.LoginDto;
import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.admin.service.LoginService;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author qun.xu
 * @desc   验证Controller
 * @date   2019/01/10
 */
@RestController
@RequestMapping("/cms/auth")
public class AuthController extends BaseController {

    @Resource LoginService loginService;

    @PostMapping(value = "/login")
    @LoginRequired(false)
    public ApiResult login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        String account = loginDto.getAccount();
        String password = loginDto.getPassword();
        UserSession userSession = loginService.login(account, password);
        String id = request.getSession().getId();
        request.getSession().setAttribute(UserSession.NAME,userSession);
        return  ApiResult.ok();
    }


    @ApiOperation("退出登录")
    @PostMapping(value = "/loginOut")
    @LoginRequired(false)
    public ApiResult loginOut(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute(UserSession.NAME);
        return ApiResult.ok();
    }
}