package cn.jdcloud.medicine.mall.api.token.dao;

import cn.jdcloud.medicine.mall.domain.token.Token;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * token凭证操作接口实现
 */
@Repository
public class TokenDao {


    @Resource(name="jdkTemplate")
    private RedisTemplate redisTemplate;
    /**
     * 保存token
     *
     * @param token token对象
     */
    public void save(Token token, String accTokenKey, String refTokenKey) {

        BoundValueOperations<Integer, String> bvo = redisTemplate.boundValueOps(accTokenKey + token.getUserId());
        bvo.set(token.getAccessToken());
        bvo.expire(Token.ACC_TOKEN_EXP, TimeUnit.SECONDS);
        //保持refreshToken
        bvo = redisTemplate.boundValueOps(refTokenKey + token.getUserId());
        bvo.set(token.getRefreshToken());
        bvo.expire(Token.REF_TOKEN_EXP, TimeUnit.MILLISECONDS);
    }

    /**
     * 删除rtoken对象
     */
    public void deleteaccToken(Integer uid,String accTokenKey) {
        redisTemplate.delete(accTokenKey + uid);
    }

    public void deleteRefToken(Integer uid,String refTokenKey) {
        redisTemplate.delete(refTokenKey + uid);
    }
    /**
     * 获取accessToken
     */
    public boolean checkAccToken(Integer uid,String accToken,String accTokenKey) {
        BoundValueOperations<Integer, String> bvo = redisTemplate.boundValueOps(accTokenKey + uid);
        String tokenStr = bvo.get();
        return accToken.equals(tokenStr);
    }

    /**
     * 判断登录并保持accessToken
     */
    public boolean checkLogin(Integer uid,String accToken,String accTokenKey) {
        BoundValueOperations<Integer, String> bvo = redisTemplate.boundValueOps(accTokenKey + uid);
        String tokenStr = bvo.get();
        long time = bvo.getExpire();
        //一个小时自动续时间
        if(time<3600){
            bvo.expire(Token.ACC_TOKEN_EXP, TimeUnit.SECONDS);
        }
        return accToken.equals(tokenStr);
    }

    /**
     * 获取refreshToken
     */
    public boolean checkRefToken(Integer uid,String refToken,String refTokenKey) {
        BoundValueOperations<Integer, String> bvo = redisTemplate.boundValueOps(refTokenKey + uid);
        String tokenStr = bvo.get();
        return refToken.equals(tokenStr);
    }


    /**
     * 判断用户是否登录
     *
     * @param userId
     * @param accessToken
     * @return
     */
    public boolean isLogined(Integer userId, String accessToken,String accTokenKey) {
        //判断用户是否登录
        boolean logined;
        if (userId == null || accessToken == null || accessToken.length() == 0) {
            logined = false;
        } else {
            logined = checkAccToken(userId,accessToken,accTokenKey);
        }
        return logined;
    }

}
