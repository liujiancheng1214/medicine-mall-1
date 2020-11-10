package cn.jdcloud.medicine.mall.api.biz.user.vo;

import cn.jdcloud.medicine.mall.domain.user.User;
import lombok.Data;

@Data
public class UserInfoVo {

	private boolean isLogin;

	private Integer id;

	private String realName;

	private String headImg;

	private String mobile;

	public UserInfoVo(boolean isLogin,User user) {
		this.isLogin = isLogin;
		if(user==null){
			return;
		}
		this.id = user.getId();
		this.mobile = user.getMobile();
	}
}
