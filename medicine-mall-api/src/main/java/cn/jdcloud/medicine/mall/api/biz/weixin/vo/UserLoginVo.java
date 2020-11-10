package cn.jdcloud.medicine.mall.api.biz.weixin.vo;

import cn.jdcloud.medicine.mall.api.biz.user.vo.UserVo;
import lombok.Data;

@Data
public class UserLoginVo {

	private boolean login;

	private UserVo  uv;

}
