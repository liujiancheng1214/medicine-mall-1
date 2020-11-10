package cn.jdcloud.medicine.mall.api.biz.user.vo;

import cn.jdcloud.medicine.mall.api.token.vo.TokenVo;
import cn.jdcloud.medicine.mall.domain.token.Token;
import cn.jdcloud.medicine.mall.domain.user.User;
import lombok.Data;

@Data
public class UserVo {

	private TokenVo token;	//token 的实体
	private User user;	//user 的实体

	/**
	 * 这里是返回一个vo vo里面包含了Token的实体和UserBase   等于是把两个实体类放在一个实体类里面返回
	 * @param token
	 * @param user
	 */
	public UserVo(Token token, User user) {
		this.token = new TokenVo();
		this.user =new User();
		this.token.setAccessToken(token.getAccessToken());
		this.token.setCreateTime(token.getCreateTime());
		this.token.setExpiresIn(token.getExpiresIn());
		this.token.setRefreshToken(token.getRefreshToken());
		this.user.setId(user.getId());
		this.user.setType(user.getType());
		this.user.setMobile(user.getMobile());
		this.user.setOpenid(user.getOpenid());
	}
}
