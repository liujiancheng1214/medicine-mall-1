package cn.jdcloud.medicine.mall.api.biz.admin.service;

import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminLoginVo;
import cn.jdcloud.medicine.mall.client.user.UserSession;

/**
 * @author qun.xu
 * @desc
 * @date 2018/9/12 14:17
 */
public interface LoginService {

    /**
     * @desc  登录
     * @param account
     * @param password
     * @return UserSession
     */
    UserSession login(String account, String password);

    void loginOut(String accessToken, int userId) throws Exception;

    AdminLoginVo getLoginInfo();
}
