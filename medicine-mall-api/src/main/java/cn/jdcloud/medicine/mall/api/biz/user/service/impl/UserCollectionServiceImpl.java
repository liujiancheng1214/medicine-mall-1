package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserCollectionService;
import cn.jdcloud.medicine.mall.dao.user.UserCollectionMapper;
import cn.jdcloud.medicine.mall.domain.user.UserCollection;

@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection>  implements UserCollectionService {

	@Autowired
	private ItemService itemService;
	@Autowired
	private UserCollectionMapper userCollectionMapper;
	
	@Override
	public List<ItemVo> listUserCollection(Integer userId, int pageNum, int pageSize) {
		List<ItemVo> voList=new ArrayList<>();
		Page<UserCollection> page=new Page();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		page =this.page(page, new QueryWrapper<UserCollection>().eq("user_id", userId).orderByDesc("create_time"));
		List<UserCollection> list=page.getRecords();
		if(list!=null&&list.size()>0) {
			List<String> itemNos=list.stream().map(bean->bean.getItemNo()).collect(Collectors.toList());
		   for(String itemNo:itemNos) {
			  ItemVo itemVo= itemService.queryItemVoByItemNo(itemNo);
			  voList.add(itemVo);
		   }
		}
		return voList;
	}


	@Override
	public int addUserCollection(Integer userId, String itemNo) {
		UserCollection userCollection=queryUserCollectionByUserIdAndItemNo(userId, itemNo);
		if(userCollection==null) {
			userCollection	=new UserCollection();
			userCollection.setId(0);
			userCollection.setItemNo(itemNo);
			userCollection.setUserId(userId);
			userCollection.setCreateTime(new Date());
			return userCollectionMapper.insert(userCollection);
		}
		else {
			userCollection.setCreateTime(new Date());
			return userCollectionMapper.updateById(userCollection);
		}
	}

	@Override
	public int deleteUserCollection(Integer userId, List<String> itemNos) {
		List<UserCollection> list=userCollectionMapper.selectList(new QueryWrapper<UserCollection>().eq("user_id", userId).in("item_no", itemNos));
		List<Integer> ids=list.stream().map(bean->bean.getId()).collect(Collectors.toList());
		return userCollectionMapper.deleteBatchIds(ids);
	}


	@Override
	public UserCollection queryUserCollectionByUserIdAndItemNo(Integer userId, String itemNo) {
		List<UserCollection> list=	userCollectionMapper.selectList(new QueryWrapper<UserCollection>().eq("user_id", userId).eq("item_no", itemNo));
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
