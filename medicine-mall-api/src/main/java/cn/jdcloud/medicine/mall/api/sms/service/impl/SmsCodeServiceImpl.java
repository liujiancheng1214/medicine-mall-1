package cn.jdcloud.medicine.mall.api.sms.service.impl;


import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;
import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;
import cn.jdcloud.medicine.mall.api.sms.redis.SmsCodeDao;
import cn.jdcloud.medicine.mall.api.sms.service.SmsCodeService;
import cn.jdcloud.medicine.mall.api.sms.service.SmsMessageService;
import cn.jdcloud.medicine.mall.api.sms.util.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service("smsCodeService")
@Slf4j
public class SmsCodeServiceImpl implements SmsCodeService {

	@Resource
    SmsMessageService smsMessageService;

	@Resource
    SmsCodeDao smsCodeDao;

	@Override
    public int save(String mobile, SmsType type) throws ApiException,ClientException {
		return save(mobile,type, SmsConstant.ALIYUN_KEY,SmsConstant.ALIYUN_SECRET,SmsConstant.DEFAULT_SMS_TITLE);
    }
	@Override
	public int save(String mobile, SmsType type,String aliyunKey,String aliPassword,String title) throws ApiException,ClientException {
		SmsUtils.checkMobile(mobile);
		//测试环境不发短信
		if(SmsConstant.IS_TEST){
			return SmsConstant.DY_SUCCESS;
		}
		String smsCode = "";
		Random ran = new Random();
		for(int i = 0; i < SmsConstant.CODE_LEN; i++) {
			smsCode = smsCode + ran.nextInt(10);
		}
		//第三方发送短信
		int result = smsMessageService.sendTemplateMessage(mobile, type, new String []{smsCode},aliyunKey,aliPassword,title);
		if(result == SmsConstant.DY_SUCCESS) {
			smsCodeDao.save(mobile, type, smsCode);
			log.info("电话号码================="+mobile+"验证码==================="+smsCode+"类型===================="+type);
			return SmsConstant.DY_SUCCESS;
		}else{
			return result;
		}
	}



    @Override
    public void delete(String mobile, SmsType type) {
        smsCodeDao.delete(mobile, type);
    }

    public String get(String mobile, SmsType type) {
        return smsCodeDao.get(mobile, type);
    }

	/**
	 * 验证码是否正确并删除
	 * @param mobile
	 * @param type
	 * @param smsCode
	 * @return
	 */
	@Override
	public void verifyAndDelete(String mobile, SmsType type, String smsCode) throws ApiException {
		verify(mobile, type, smsCode);
		delete(mobile, type);
}

	/**
	 * 验证码是否正确
	 * @param mobile
	 * @param type
	 * @param smsCode
	 * @return
	 */
	@Override
	public void verify(String mobile, SmsType type, String smsCode) throws ApiException {
		//测试环境不做校验
		if(SmsConstant.IS_TEST&& "1234".equals(smsCode)){
			return ;
		}
		String smsCodeResult = get(mobile, type);
		if(!smsCode.equals(smsCodeResult)){
			throw new ApiException(UserCode.VERIFY_CODE_ERROR);
		}
	}

}
