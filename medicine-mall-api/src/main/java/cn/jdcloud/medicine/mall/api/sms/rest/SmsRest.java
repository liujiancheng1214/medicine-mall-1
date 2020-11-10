package cn.jdcloud.medicine.mall.api.sms.rest;


import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;
import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;
import cn.jdcloud.medicine.mall.api.sms.service.SmsCodeService;
import cn.jdcloud.medicine.mall.api.sys.utils.CheckUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value="/sms")
@LoginRequired(value = false)
public class SmsRest extends BaseController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private SmsCodeService smsCodeService;

	/**
	 * 获取短信验证码
	 * @param mobile 电话号码
	 * @param type		发送短信类型
	 * @return 封装的消息类
	 * @throws Exception
	 */
	@RequestMapping(value = "validCode",method = RequestMethod.POST)
	public ApiResult validCode(
			@RequestHeader(value = "userType",required = true) byte userType,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "type",required = true) Integer type) throws Exception {

		CheckUtils.checkMobile(mobile);
		SmsType smsType = SmsType.get(type);
		if(smsType == null){
			throw new ApiException(SysCodeEnums.PARAMS_ERROR);
		}
		int result  = smsCodeService.save(mobile, smsType);
		switch (result) {
			case SmsConstant.DY_FAULT:
				throw new ApiException(UserCode.SEND_SMSMESSAGE_FAIL);
			case SmsConstant.DY_MOBILE_ERROR:
				throw new ApiException(UserCode.MOBILE_IS_WRONGFULNESS);
			case SmsConstant.DY_SURPASS_LIMIT:
				throw new ApiException(UserCode.VERIFY_CODE_FAILED_COUNT_UPPER_LIMIT);
			default:break;
		}
		return ApiResult.ok();
	}

}