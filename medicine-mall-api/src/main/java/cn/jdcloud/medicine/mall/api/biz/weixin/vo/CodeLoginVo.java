package cn.jdcloud.medicine.mall.api.biz.weixin.vo;

import cn.jdcloud.medicine.mall.api.biz.user.vo.UserVo;
import cn.jdcloud.medicine.mall.domain.token.Token;
import cn.jdcloud.medicine.mall.domain.user.User;
import lombok.Data;

@Data
public class CodeLoginVo {

	private Integer status;

	private UserVo  uv;

	private WeiXinUserInfoVo wv;

}
