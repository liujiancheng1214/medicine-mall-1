package cn.jdcloud.medicine.mall.api.biz.sign;

import cn.jdcloud.medicine.mall.api.biz.sign.vo.SignVo;
import cn.jdcloud.medicine.mall.domain.sige.Sign;

public interface SignService {

	void updateUserSignTag(Integer userId ,byte tag);
	
	Sign queryCurrentDaySign(Integer userId);
	
	int addSign(Integer userId);
	
	SignVo  querySignVo(Integer userId);
}
