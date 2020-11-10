package cn.jdcloud.medicine.mall.api.biz.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.domain.user.UserCollection;

public interface UserCollectionService extends IService<UserCollection> {

	UserCollection queryUserCollectionByUserIdAndItemNo(Integer userId,String itemNo);
	
	List<ItemVo> listUserCollection(Integer userId,int pageNum,int pageSize);
	
	
	int addUserCollection(Integer userId,String itemNo);
	
	int deleteUserCollection(Integer userId,List<String> itemNos);
}
