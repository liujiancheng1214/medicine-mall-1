package cn.jdcloud.medicine.mall.api.sms.redis.impl;

import cn.jdcloud.medicine.mall.api.sms.enums.SmsType;
import cn.jdcloud.medicine.mall.api.sms.redis.SmsCodeDao;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Repository
public class SmsCodeDaoImpl implements SmsCodeDao {
	
	private final Long expire = 600000L;

	private static final String SMS_PRE = "fruit:sms"+":";

    private static final String SMS_COUNT = "SMS_COUNT_LIMIT";
	
	@Resource(name="jdkTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public void save(String mobile, SmsType type, String smsCode) {
        BoundValueOperations<String, String> bvo = redisTemplate.boundValueOps(SMS_PRE + type.getTemplate() + mobile);
        bvo.set(smsCode);
        bvo.expire(expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(String mobile, SmsType type) {
        redisTemplate.delete(SMS_PRE + type.getTemplate() + mobile);
    }

    @Override
    public String get(String mobile, SmsType type) {
        BoundValueOperations<String, String> bvo = redisTemplate.boundValueOps(SMS_PRE + type.getTemplate() + mobile);
        return bvo.get();
    }


    @Override
    public void saveTodayCountSms(int count) {
        BoundValueOperations<String, Integer> bvo = redisTemplate.boundValueOps(SMS_COUNT);
        //获取今天23:59:59的毫秒值
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
        Long expired = calendar.getTime().getTime()/1000;
        Long now = System.currentTimeMillis()/1000;
        Long seconds = expired - now;
        bvo.set(count);
        //这里是秒
        bvo.expire(seconds, TimeUnit.SECONDS);
    }

    @Override
    public Integer getTodayCountSms() {
        BoundValueOperations<String, Integer> bvo = redisTemplate.boundValueOps(SMS_COUNT);
        Integer count = bvo.get();
        if (count == null) {
            count = 0;
        }
        return count;
    }

}
