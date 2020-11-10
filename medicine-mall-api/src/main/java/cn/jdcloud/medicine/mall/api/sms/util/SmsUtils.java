package cn.jdcloud.medicine.mall.api.sms.util;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;

/**
 * 检查工具类
 * @author microstrong
 *
 */
public class SmsUtils {
	
	/**
	 * 检查手机号码
	 * @param mobile
	 * @throws ApiException
	 */
	public static void checkMobile(String mobile) throws ApiException {
		if(StringUtils.isBlank(mobile) || mobile.length()!=11
				|| !StringUtils.isNumeric(mobile) || "1".equals(mobile.charAt(0)) ){
			throw new ApiException(UserCode.MOBILE_IS_WRONGFULNESS);
		}
	}

	/**
	 * 检查手机验证码
	 * @param smsCode
	 * @throws ApiException
     */
	public static void checkSmsCode(String smsCode) throws ApiException {
		if (StringUtils.isEmpty(smsCode) || smsCode.length() != SmsConstant.CODE_LEN || !smsCode.matches("[0-9]+")) {
			throw new ApiException(UserCode.SMS_CODE_IS_WRONGFULNESS);
		}

	}

}
