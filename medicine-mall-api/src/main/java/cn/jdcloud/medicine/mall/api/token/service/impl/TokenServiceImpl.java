package cn.jdcloud.medicine.mall.api.token.service.impl;

import cn.jdcloud.medicine.mall.api.token.service.TokenService;
import cn.jdcloud.medicine.mall.api.token.dao.TokenDao;
import cn.jdcloud.medicine.mall.domain.token.Token;
import cn.jdcloud.medicine.mall.domain.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * toke业务服务实现
 * 
 * @author WangYang
 * @version 1.0
 * @datetime 2015/10/27 12:56
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

	private static final String ACC_GROWER_TOKEN_PRE = "fruit:grower:USER_ACC_TOKEN_";

	private static final String REF_GROWER_TOKEN_PRE = "fruit:grower:USER_REF_TOKEN_";

	private static final String ACC_SELLER_TOKEN_PRE = "fruit:seller:USER_ACC_TOKEN_";

	private static final String REF_SELLER_TOKEN_PRE = "fruit:seller:USER_REF_TOKEN_";

	private static final String ACC_CUSTOMER_TOKEN_PRE = "fruit:customer:USER_ACC_TOKEN_";

	private static final String REF_CUSTOMER_TOKEN_PRE = "fruit:customer:USER_REF_TOKEN_";

	private static final String ACC_ADMIN_TOKEN_PRE = "fruit:admin:USER_ACC_TOKEN_";

	private static final String REF_ADMIN_TOKEN_PRE = "fruit:admin:USER_REF_TOKEN_";

	@Resource
	private TokenDao tokenDao;

	/**
	 * 保存token
	 *
	 * @param token token对象
	 */
	@Override
	public void saveAccessToken(Token token,byte type) {
		tokenDao.save(token,getAccKey(type),getRefKey(type));
	}

	/**
	 * 删除token对象
	 */
	@Override
	public void deleteAccToken(Integer uid,byte type) {
		tokenDao.deleteaccToken(uid,getAccKey(type));
	}

	/**
	 * 获取accessToken
	 *
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkAccToken(int uid,String accToken,byte type) throws Exception {
		return tokenDao.checkAccToken(uid,accToken,getAccKey(type));
	}

	@Override
	public boolean checkLogin(int uid, String accToken, byte type) throws Exception {
		return tokenDao.checkLogin(uid,accToken,getAccKey(type));
	}

	/**
	 * 获取accessToken
	 *
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkRefToken(int uid,String refToken,byte type) throws Exception {
		return tokenDao.checkAccToken(uid,refToken,getRefKey(type));
	}

	/**
	 * 刷新token
	 *
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Token refreshToken(int uid, String refreshToken,byte type) throws Exception {
		boolean result = tokenDao.checkRefToken(uid,refreshToken,getRefKey(type));
		if(!result) {
			return null;
		}
		Token token = new Token(uid);
		tokenDao.deleteRefToken(uid,getRefKey(type));
		tokenDao.save(token,getAccKey(type),getRefKey(type));
		return token;
	}

	private String  getAccKey(byte type){
		switch (type){
			case User.GROWER:
				return ACC_GROWER_TOKEN_PRE;
			case User.SELLER:
				return ACC_SELLER_TOKEN_PRE;
			case User.CUSTOMER:
				return ACC_CUSTOMER_TOKEN_PRE;
			case User.ADMIN:
				return ACC_ADMIN_TOKEN_PRE;
			default:
				return "";
		}
	}

	private String  getRefKey(byte type){
		switch (type){
			case User.GROWER:
				return REF_GROWER_TOKEN_PRE;
			case User.SELLER:
				return REF_SELLER_TOKEN_PRE;
			case User.CUSTOMER:
				return REF_CUSTOMER_TOKEN_PRE;
			case User.ADMIN:
				return REF_ADMIN_TOKEN_PRE;
			default:
				return "";
		}
	}
}
