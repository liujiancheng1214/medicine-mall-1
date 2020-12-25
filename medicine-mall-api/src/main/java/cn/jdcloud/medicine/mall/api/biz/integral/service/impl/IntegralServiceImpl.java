package cn.jdcloud.medicine.mall.api.biz.integral.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.medicine.mall.api.biz.integral.service.IntegralService;
import cn.jdcloud.medicine.mall.api.biz.integral.service.vo.IntegralVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.integral.IntegralMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.integral.Integral;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.user.User;


@Service
public class IntegralServiceImpl implements IntegralService {

	@Autowired
	private IntegralMapper IntegralMapper;
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private UserMapper userMapper;
	@Override
	public Page<IntegralVo> pageIntegral(int pageNum,int pageSize,Integer userId, 
			Integer changeType) {
		Page<Integral> page=new Page<Integral>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		QueryWrapper<Integral> queryWrapper =new QueryWrapper<Integral>();
		queryWrapper.eq("user_id", userId);
		queryWrapper.eq(changeType!=null,"change_type", changeType);
		page=IntegralMapper.selectPage(page, queryWrapper);
		
		Page<IntegralVo> pageVo=new Page<IntegralVo>();
		BeanUtil.copyProperties(page, pageVo,"records");
		List<IntegralVo> list=new ArrayList<>();
		list=BeanUtil.copyPropsForList(page.getRecords(), IntegralVo.class);
		pageVo.setRecords(list);
		return pageVo;
	}


	/**
	 * 获得积分
	 */
	@Override
	@Transactional
	public void obtainIntegral(Integer userId, String type, Integer itemId) {
		Integral  integral=new Integral();
		integral.setId(0);
		integral.setCreateTime(new Date());
		integral.setType(type);
		integral.setUserId(userId);
		integral.setChangeType(Integral.CHANGE_TYPE_OBTAIN);
		Integer amount=0;
		String reamrk = null;
	    String typeDesc = null;
		
		switch(type) {
			case Integral.INTEGRAL_TYPE_SIGN:
				amount=100;
				typeDesc="签到";
				reamrk=DateUtils.today()+"签到获得积分";
				break;
			case Integral.INTEGRAL_TYPE_SIGN_ADDITIONAL:
				amount=300;
				typeDesc="签到";
				reamrk=DateUtils.today()+"连续7天签到获得积分";
				break;
			case Integral.INTEGRAL_TYPE_BUY_ITEM:
				Item item=itemMapper.selectById(itemId);
				amount=item.getIntegral();
				typeDesc="购买："+item.getItemName();
				reamrk=typeDesc;
				break;
		}
		integral.setAmount(amount);
		integral.setTypeDesc(typeDesc);
		integral.setReamrk(reamrk);
		// 积分明细
		IntegralMapper.insert(integral);
		//用户总积分
		User user=userMapper.selectById(userId);
		user.setIntegral(user.getIntegral()+amount);
		userMapper.updateById(user);
	}

	/**
	 * 使用积分
	 */
	@Override
	public void useIntegral(Integer userId, String type, Integer itemId, Integer couponId) {
	
		
	}

}
