package cn.jdcloud.medicine.mall.api.sms.service.impl;

import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;
import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;
import cn.jdcloud.medicine.mall.api.sms.redis.SmsCodeDao;
import cn.jdcloud.medicine.mall.api.sms.service.SmsMessageService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("smsMessageService")
public class SmsMessageServiceImpl implements SmsMessageService {

	@Resource(name="gson")
	private Gson gson;

	 Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private SmsCodeDao smsCodeDao;

	private static final int SMS_COUNT = 50;
	/**
	 * @author 淘宝大鱼发送短信方法
	 * @param mobile 手机号
	 * @param type 内容模板名
	 * @param args 占位参数
	 * @return
	 */
	@Override
	public int sendTemplateMessage(String mobile, SmsType type, String[]args, String aliyunKey, String aliyunSecret, String title)
			throws ClientException {
		if(SmsConstant.IS_TEST){
			return SmsConstant.DY_SUCCESS;
		}
		//超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化acsClient,暂不支持region化
		IClientProfile  profile =
				DefaultProfile.getProfile("cn-hangzhou",aliyunKey, aliyunSecret);

		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", SmsConstant.PRODUCT,SmsConstant.ALIYUN_URL);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers(mobile);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(title);
		//必填:短信模板-可在短信控制台中找到
		String templateCode = type.getTemplate();
		request.setTemplateCode(templateCode);
		//设置通用参数，必须在短信模板中以arg命名短信服务
		request.setTemplateParam(getParamString(args));
		//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
//		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//		request.setOutId("yourOutId");
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if(!"OK".equals(sendSmsResponse.getCode())){
			logger.warn("手机号"+mobile+"发送模板:"+type.getTemplate()+"失败，失败Code:"+sendSmsResponse.getCode());
			return SmsConstant.DY_FAULT;
		}
		return SmsConstant.DY_SUCCESS;
	}

	/**
	 * 根据模板立即发送短信
	 * @param mobile 手机号
	 * @param type 内容模板名
	 * @param args 占位参数
	 * @return
	 */
	@Override
	public  int sendTemplateMessageByDefault(String mobile, SmsType type, String[]args) throws ClientException{
			this.sendTemplateMessage(mobile, type, args, SmsConstant.ALIYUN_KEY, SmsConstant.ALIYUN_SECRET, SmsConstant.DEFAULT_SMS_TITLE);
		return SmsConstant.DY_SUCCESS;
	}

	private String getParamString(String[] args){
		Map<String,String> map = new HashMap<>();
		for(int i=0;i<args.length;i++){
			map.put("arg"+i,args[i]);
		}
		return gson.toJson(map);
	}

	public static void main(String[] args) {
		String a = "[aa,bb,cc]";
		Gson gson = new Gson();
		String [] b= gson.fromJson(a, String[].class);
		System.out.println(b);
		for (int i= 0; i<b.length;i++) {
			System.out.println(b[i]);
		}
	}

//暂时没有用到
//	private ResourceBundleMessageSource getMessageSource(){
//		ResourceBundleMessageSource messageSource =
//				(ResourceBundleMessageSource) SpringContextUtil.getBean("messageSource");
//		return messageSource;
//	}
//
}
