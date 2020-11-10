package cn.jdcloud.medicine.mall.api.sys.utils;

import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.IdCardUtil;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;

import java.util.Collection;

/**
 * 检查工具类
 * @author microstrong
 *
 */
public class CheckUtils {
	

	public static void checkMobile(String mobile) throws ApiException {
		
		if(StringUtils.isBlank(mobile) || mobile.length()!=11
				|| !StringUtils.isNumeric(mobile) || "1".equals(mobile.charAt(0)) ){
			throw new ApiException(UserCode.MOBILE_IS_WRONGFULNESS);
		}
	}
	
	/**
	 * 检查身份证号码
	 * @param idCardNo
	 * @throws ApiException
	 */
	public static void checkIdCardNo(String idCardNo) throws ApiException{
		if(StringUtils.isBlank(idCardNo)) {
			throw new ApiException(UserCode.ID_CARD_IS_WRONGFULNESS);
		}
		if(!IdCardUtil.idCardValidate(idCardNo)){
			throw new ApiException(UserCode.ID_CARD_IS_WRONGFULNESS);
		}
	}
	


	/**
	 * 检查密码
	 * @param password
	 * @throws ApiException
     */
	public static void checkPassword(String password) throws ApiException {
		if (StringUtils.isEmpty(password) || password.length() != 32 ) {
			throw new ApiException(UserCode.PASSWORD_IS_WRONGFULNESS);
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

	public static void checkParamEmpty(Object param)throws ApiException{
		if(param==null){
			throw new ApiException(SysCodeEnums.PARAMS_IS_NULL);
		}
		if(param instanceof String){
			if(((String) param).length()==0){
				throw new ApiException(SysCodeEnums.PARAMS_IS_NULL);
			}
		}
		if(param instanceof Collection) {
			if(((Collection) param).size()==0){
				throw new ApiException(SysCodeEnums.PARAMS_SIZE_ZERO);
			}
		}
	}
}
