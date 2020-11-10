package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.user.service.UserAddressService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressAddVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressUpdateVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.user.UserAddressMapper;
import cn.jdcloud.medicine.mall.domain.user.UserAddress;

@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

	@Autowired
	private UserAddressMapper userAddressMapper;

	@Override
	public UserAddressVo addUserAddressVo(Integer userId, UserAddressAddVo userAddressAddVo) {
		UserAddress userAddress = new UserAddress();
		userAddress.setId(0);
		userAddress.setCreateTime(new Date());
		userAddress.setUpdateTime(new Date());
		userAddress.setUserId(userId);
		BeanUtil.copyProperties(userAddressAddVo, userAddress);
		userAddressMapper.insert(userAddress);
		return chengeUserAddress(userAddress);
	}

	private UserAddressVo chengeUserAddress(UserAddress userAddress) {
		UserAddressVo vo = new UserAddressVo();
		BeanUtil.copyProperties(userAddress, vo);
		return vo;
	}

	@Override
	public List<UserAddressVo> listUserAddressByUserId(Integer userId) {
		QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<UserAddress>();
		queryWrapper.eq("user_id", userId).eq("is_deleted", 0).orderByDesc("update_time");
		List<UserAddress> list = userAddressMapper.selectList(queryWrapper);
		List<UserAddressVo> voList = new ArrayList<>();
		for (UserAddress add : list) {
			voList.add(chengeUserAddress(add));
		}
		return voList;
	}

	@Override
	@Transactional
	public UserAddressVo setDefaultAddress(Integer userId, Integer addressId) {
		QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<UserAddress>();
		queryWrapper.eq("user_id", userId).eq("is_deleted", 0).eq("is_default", 1);
		List<UserAddress> list = userAddressMapper.selectList(queryWrapper);
		UserAddress userAddress;
		if (list.size() > 0) {
			userAddress = list.get(0);
			userAddress.setIsDefault((byte) 0);
			userAddressMapper.updateById(userAddress);
		}
		userAddress = userAddressMapper.selectById(addressId);
		if (userAddress != null) {
			userAddress.setIsDefault((byte) 1);
			userAddress.setUpdateTime(new Date());
			userAddressMapper.updateById(userAddress);
			return chengeUserAddress(userAddress);
		}
		return null;
	}

	@Override
	public int deleteUserAddress(Integer userId, Integer addressId) {
		UserAddress userAddress = userAddressMapper.selectById(addressId);
		if (userAddress != null && userAddress.getUserId().equals(userId)) {
			userAddress.setIsDeleted((byte) 1);
			return userAddressMapper.updateById(userAddress);
		}
		return 0;
	}

	
	@Override
	@Transactional
	public UserAddressVo updateUserAddress(Integer userId, UserAddressUpdateVo userAddressUpdateVo) {
		UserAddress userAddress = userAddressMapper.selectById(userAddressUpdateVo.getId());
		if (userAddress != null && userAddress.getUserId().equals(userId)) {
			BeanUtil.copyProperties(userAddressUpdateVo, userAddress);
			userAddress.setUpdateTime(new Date());
			userAddressMapper.updateById(userAddress);
			if(userAddressUpdateVo.getIsDefault()==1) {
				setDefaultAddress(userId, userAddressUpdateVo.getId());
			}
			userAddress = userAddressMapper.selectById(userAddressUpdateVo.getId());
			return chengeUserAddress(userAddress);
		}
		return null;
	}

	@Override
	public UserAddressVo queryDefaultAddress(Integer userId) {
		QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<UserAddress>();
		queryWrapper.eq("user_id", userId).eq("is_deleted", 0).eq("is_default", 1);
		List<UserAddress> list=userAddressMapper.selectList(queryWrapper);
		if(list.size()>0) {
		    return chengeUserAddress(list.get(0));
		}
		return null;
	}
}
