package cn.jdcloud.medicine.mall.api.biz.sign.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.medicine.mall.api.biz.integral.service.IntegralService;
import cn.jdcloud.medicine.mall.api.biz.sign.SignService;
import cn.jdcloud.medicine.mall.api.biz.sign.code.SignCode;
import cn.jdcloud.medicine.mall.api.biz.sign.vo.SignVo;
import cn.jdcloud.medicine.mall.dao.sign.SignMapper;
import cn.jdcloud.medicine.mall.domain.integral.Integral;
import cn.jdcloud.medicine.mall.domain.sige.Sign;


@Service
public class SignServiceImpl implements SignService {

	 @Autowired
	 private SignMapper signMapper;
	 @Autowired
	 IntegralService  integralService;
	
	private Sign querySignByUserIdAndDate(Integer userId,String date) {
		List<Sign>  list=signMapper.selectList(new QueryWrapper<Sign>().eq("user_id", userId)
				.eq("create_time", date));
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public int addSign(Integer userId) {
		// 先查询当天是否签到 若已经签到 则不允许再签到
		Sign sign=queryCurrentDaySign(userId);
		if(sign != null) {
			//  抛异常
			throw new ApiException(SignCode.SIGN_STATUS_ERROR);
		}
		sign=querySignByUserIdAndDate(userId, DateUtils.getYesterday());
		if(sign==null) {
			sign = new Sign();
			sign.setId(0);
			sign.setCreateTime(DateUtils.today());
			sign.setContinuityDay(1);
			sign.setUserId(userId);
			signMapper.insert(sign);
			//  增加积分
			integralService.obtainIntegral(userId, Integral.INTEGRAL_TYPE_SIGN, null);
		}
		else {
			int day=sign.getContinuityDay();
			Sign newSign=new Sign();
			newSign.setId(0);
			newSign.setCreateTime(DateUtils.today());
			newSign.setUserId(userId);
			boolean additionalIntegral = false;
			if(day==7) {
				additionalIntegral=true;
				newSign.setContinuityDay(1);
			}
			else {
				newSign.setContinuityDay(sign.getContinuityDay()+1);
			}
			signMapper.insert(newSign);
			
			if(additionalIntegral) {
				//  额外积分赠送
				integralService.obtainIntegral(userId, Integral.INTEGRAL_TYPE_SIGN_ADDITIONAL, null);
			}
			else {
				//  正常积分赠送
				integralService.obtainIntegral(userId, Integral.INTEGRAL_TYPE_SIGN, null);
			}
		}
		return 0;
	}

	@Override
	public SignVo querySignVo(Integer userId) {
		// 查询总签到数  以及点亮第一天 当天是否可签到
		int totalSign=signMapper.selectCount(new QueryWrapper<Sign>().eq("user_id", userId));
		int continuityDay=0;
		int currentDaySignTag=0;
		// 查询最新的记录
		Page<Sign> page=new Page<Sign>();
		page.setCurrent(1);
		page.setSize(1);
		page=signMapper.selectPage(page,new QueryWrapper<Sign>().eq("user_id", userId).orderByDesc("create_time"));
		List<Sign> list=page.getRecords();
		Sign sign=null;
		if(list.size()>0) {
			 sign=list.get(0);
			String creatTime=sign.getCreateTime();
			// 如果是今天或者昨天  则直接取continuityDay
			if(creatTime.equals(DateUtils.today())||creatTime.equals(DateUtils.getYesterday())) {
				continuityDay=sign.getContinuityDay();
			}
		}
		// 判断当天是否可以签到
		sign=queryCurrentDaySign(userId);
		if(sign==null) {
			currentDaySignTag=1;
		}
		
		SignVo  signVo=new SignVo();
		signVo.setContinuityDay(continuityDay);
		signVo.setCurrentDaySignTag(currentDaySignTag);
		signVo.setTotalSign(totalSign);
		return signVo;
	}
	


	@Override
	public Sign queryCurrentDaySign(Integer userId) {
		return querySignByUserIdAndDate(userId, DateUtils.today());
	}

}
