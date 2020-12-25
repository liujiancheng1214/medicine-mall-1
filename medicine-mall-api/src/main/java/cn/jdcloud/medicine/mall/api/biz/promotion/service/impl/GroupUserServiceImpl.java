package cn.jdcloud.medicine.mall.api.biz.promotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupUserService;
import cn.jdcloud.medicine.mall.dao.promotion.GroupUserMapper;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUser;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUserResult;

/**
 * @author chenQF
 * @desc 拼团记录业务层
 * @date 2020/8/24 0024 13:53
 */
@Service
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {

    @Resource
    GroupUserMapper groupUserMapper;

    @Override
    public Page<GroupUserResult> listGroupUser(Page<GroupUserResult> page, String groupId) {
        page.setRecords(groupUserMapper.listGroupUser(page, groupId));
        page.setTotal(groupUserMapper.listCount(groupId));
        return page;
    }

	@Override
	public GroupUser queryGroupUserByUserIdAndOrderId(Integer userId, String orderId) {
		List<GroupUser> list= groupUserMapper.selectList(new QueryWrapper<GroupUser>()
				.eq("user_id", userId).eq("order_id", orderId));
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
