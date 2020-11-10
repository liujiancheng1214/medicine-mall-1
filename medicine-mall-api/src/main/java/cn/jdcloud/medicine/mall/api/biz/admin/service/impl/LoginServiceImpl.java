package cn.jdcloud.medicine.mall.api.biz.admin.service.impl;

import cn.jdcloud.medicine.mall.api.biz.admin.service.LoginService;
import cn.jdcloud.medicine.mall.api.biz.admin.service.PermissionService;
import cn.jdcloud.medicine.mall.api.biz.admin.service.RoleService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminLoginVo;
import cn.jdcloud.medicine.mall.domain.admin.Admin;
import com.alibaba.fastjson.JSON;
import cn.jdcloud.medicine.mall.api.biz.admin.code.AdminCode;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.dao.admin.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;


/**
 * @author qun.xu
 * @desc   后台管理员的服务接口实现
 * @date   2018/9/12 14:18
 */
@Service
public class LoginServiceImpl implements LoginService {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    AdminMapper adminMapper;

    @Resource
    RoleService roleService;
    @Resource
    PermissionService permissionService;

    @Override
    public UserSession login(String account, String password) {
        if (StringUtils.isEmpty(account)){
            throw new ApiException(AdminCode.ACCOUNT_IS_EMPTY);
        }
        if (StringUtils.isEmpty(password)){
            throw new ApiException(AdminCode.PASSWORD_IS_EMPTY);
        }
        //查询admin
        Admin admin = adminMapper.getByAccount(account);
        if(admin==null){
            admin = adminMapper.selectByMobile(account);
        }
        if(admin==null){
            throw new ApiException(AdminCode.ADMIN_IS_NOT_EXIST);
        }
        if(!password.equals(admin.getPassword())){
            throw new ApiException(AdminCode.PASSWORD_IS_ERROR);
        }
        UserSession userSession = new UserSession();
        Integer adminId = admin.getId();
        Set<Integer> roleIds = roleService.findRolesByAdminId(adminId);
        Set<String> permissionValues = permissionService.findPermissionValusByRoleIds(roleIds);
        userSession.setUserId(adminId);
        userSession.setAccount(account);
        userSession.setUserName(admin.getName());
        userSession.setRoleValue(roleIds);
        userSession.setPermissionValue(permissionValues);
        userSession.setHeadImg(admin.getHeadImg());
        return userSession;
    }
    @Override
    public AdminLoginVo getLoginInfo() {
        Admin admin = new Admin();
        logger.info("#################get user info：user info{}############", JSON.toJSONString(admin));
        AdminLoginVo adminLoginVo = new AdminLoginVo(admin);
        return adminLoginVo;
    }
    /**
     * 退出登录
     * @param accessToken   令牌
     * @param userId        用户Id
     * @throws              ApiException
     */
    @Override
    public void loginOut(String accessToken, int userId) throws Exception {
//        //根据userId取出Token
//        String token = tokenService.getAccessTokenByUid(userId,Token.ADMIN);
//        if (accessToken.equals(token)) {
//            tokenService.deleteAllToken(userId,Token.ADMIN);
//        }
        //清除后台的Session
//        SecurityUtils.getSubject().logout();
    }
}
