package cn.jdcloud.medicine.mall.api.common.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.domain.token.Token;

@Component
public class UserContextUtil {
	
	 @Autowired
	 private  RedisTemplate stringTemplate;
	 
	 
	 public void saveCode(String mobile ,String code) {
		 BoundValueOperations<String, String> bvo =  stringTemplate.boundValueOps(mobile);
		 bvo.set(code);
		 bvo.expire(5*60, TimeUnit.SECONDS);
	 }
	 
	 public boolean checkCode(String mobile ,String code) {
		 BoundValueOperations<String, String> bvo =  stringTemplate.boundValueOps(mobile);
		 String oraginCode=bvo.get();
		 if(oraginCode==null) {
				throw new ApiException(UserCode.VERIFY_CODE_FAILED);
		 }
		 if(!oraginCode.equals(code)) {
			 throw new ApiException(UserCode.VERIFY_CODE_ERROR);
		 }
		 return true;
	 }
	 
	public  String userIdToToken(Integer userId) {
		 String token=System.currentTimeMillis()+"";
		 BoundValueOperations<String, String> bvo =  stringTemplate.boundValueOps(token);
		 bvo.set(userId.toString());
		 bvo.expire(Token.ACC_TOKEN_EXP, TimeUnit.SECONDS);
		 return token;
		
	}
	
	public   Integer tokenToUserId(String token) {
		BoundValueOperations<String, String> bvo = stringTemplate.boundValueOps(token);
		String userId = bvo.get();
		if(userId==null) {
			throw new ApiException(UserCode.LOGIN_IS_INVALID);
		}
		long  time=bvo.getExpire();
		if(time<3600) {
		   bvo.expire(Token.ACC_TOKEN_EXP, TimeUnit.SECONDS);
		}
		return Integer.parseInt(userId);
	}
	
	public  void loginOut(String token) {
		stringTemplate.delete(token);
	}
}
