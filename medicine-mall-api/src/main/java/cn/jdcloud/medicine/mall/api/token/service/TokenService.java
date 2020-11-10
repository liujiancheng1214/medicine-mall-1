package cn.jdcloud.medicine.mall.api.token.service;


import cn.jdcloud.medicine.mall.domain.token.Token;

/**
 * token业务接口
 * @author qun.xu
 */
public interface TokenService {

    /**
     * 保存token
     *
     * @param token token对象
     */
    void saveAccessToken(Token token, byte type);

    /**
     * 删除token对象
     */
    void deleteAccToken(Integer uid, byte type);

    /**
     * 获取accessToken
     * @param uid
     * @return
     * @throws Exception
     */
    boolean checkAccToken(int uid, String accToken, byte type) throws Exception;

    /**
     * 获取accessToken
     * @param uid
     * @return
     * @throws Exception
     */
    boolean checkLogin(int uid, String accToken, byte type) throws Exception;

    /**
     * 获取accessToken
     * @param uid
     * @return
     * @throws Exception
     */
    boolean checkRefToken(int uid, String refToken, byte type) throws Exception;



    /**
     * 刷新token
     * @param uid
     * @param refreshToken
     * @return
     * @throws Exception
     */
    Token refreshToken(int uid, String refreshToken, byte type) throws Exception;

}
