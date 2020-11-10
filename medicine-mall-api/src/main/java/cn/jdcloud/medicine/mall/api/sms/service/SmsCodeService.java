package cn.jdcloud.medicine.mall.api.sms.service;

import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;
import com.aliyuncs.exceptions.ClientException;
import cn.jdcloud.framework.core.exception.ApiException;

/**
 * 验证码存取逻辑
 * @author microstrong
 *
 */
public interface SmsCodeService {

    /**
     * 保存手机验证码
     * @param mobile
     */
    int save(String mobile, SmsType type) throws ApiException,ClientException;

    /**
     * 保存手机验证码
     * @param type
     * @throws ApiException
     */
    int save(String mobile, SmsType type, String aliyunKey, String aliPassword, String title) throws ApiException,ClientException;

    /**
     * 删除手机验证码
     * @param mobile
     * @param type
     */
    void delete(String mobile, SmsType type);

    /**
     * 验证码是否正确并且删除
     * @param mobile
     * @param type
     * @param smsCode
     * @return
     */
    void verifyAndDelete(String mobile, SmsType type, String smsCode) throws ApiException;

    /**
     * 验证码是否正确
     * @param mobile
     * @param type
     * @param smsCode
     * @return
     */
    void verify(String mobile, SmsType type, String smsCode) throws ApiException;

}
