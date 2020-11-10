package cn.jdcloud.medicine.mall.api.sms.redis;

import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;

/**
 * 手机验证码
 * @author qunx.xu
 *
 */
public interface SmsCodeDao {
	
	/**
	 * 保存手机验证码
	 * @param smsCode
	 */
    void save(String mobile, SmsType type, String smsCode);

    /**
     * 删除手机验证码
     * @param mobile
     * @param type
     */
    void delete(String mobile, SmsType type);

    /**
     * 获取手机验证码
     * @param mobile
     * @param type
     * @return
     */
    String get(String mobile, SmsType type);

    /**
     * 保存模板今日使用次数
     */
    void saveTodayCountSms(int count);


    /**
     * 获取模板今日使用次数
     */
    Integer getTodayCountSms();
}
