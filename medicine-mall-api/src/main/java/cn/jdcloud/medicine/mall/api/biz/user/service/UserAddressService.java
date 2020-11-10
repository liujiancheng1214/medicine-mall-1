package cn.jdcloud.medicine.mall.api.biz.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressAddVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressUpdateVo;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressVo;
import cn.jdcloud.medicine.mall.domain.user.UserAddress;

public interface UserAddressService  extends IService<UserAddress>{

	
	UserAddressVo queryDefaultAddress(Integer userId);
	
	UserAddressVo addUserAddressVo(Integer userId  ,UserAddressAddVo userAddressAddVo);
	
	List<UserAddressVo> listUserAddressByUserId(Integer userId);
	
	UserAddressVo setDefaultAddress(Integer userId  ,Integer addressId);
	
	int deleteUserAddress(Integer userId  ,Integer addressId);
	
	UserAddressVo updateUserAddress(Integer userId,UserAddressUpdateVo userAddressUpdateVo);
}
