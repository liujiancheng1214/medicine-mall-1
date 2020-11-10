package cn.jdcloud.medicine.mall.api.biz.user.utils;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.framework.utils.security.MD5Util;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;


/**
 * 加密工具类
 * author: microstrong
 * date: 2016/3/30
 */
public class EncryptUtils {

    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPassword(String salt, String password) throws ApiException {
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(salt)) {
            throw new ApiException(UserCode.PASSWORD_ENCRYPT_ERROR);
        }
        return MD5Util.encrypt(salt + password);
    }

    /**
     * 密码加密
     * @param password
     * @return
     * @throws ApiException
     */
    public static String encryptPassword(String password) throws ApiException {
        if(StringUtils.isEmpty(password)) {
            throw new ApiException(UserCode.PASSWORD_ENCRYPT_ERROR);
        }
        return MD5Util.encrypt(password);
    }
}
