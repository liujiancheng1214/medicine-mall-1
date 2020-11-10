package cn.jdcloud.medicine.mall.api.sms.service;

import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;
import com.aliyuncs.exceptions.ClientException;


public interface SmsMessageService {
	/**
	 * 根据模板立即发送短信
	 * @param mobile 手机号
	 * @param type 内容模板名
	 * @param args 占位参数
	 * @return
	 */
	int sendTemplateMessage(String mobile, SmsType type, String[] args, String aliyunKey, String aliyunSecret, String title)
			throws ClientException;
	/**
	 * 根据模板立即发送短信
	 * @param mobile 手机号
	 * @param type 内容模板名
	 * @param args 占位参数
	 * @return
	 */
	int sendTemplateMessageByDefault(String mobile, SmsType type, String[] args) throws ClientException;
}

