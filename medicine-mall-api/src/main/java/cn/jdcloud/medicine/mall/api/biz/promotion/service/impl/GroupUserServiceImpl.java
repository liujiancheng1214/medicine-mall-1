package cn.jdcloud.medicine.mall.api.biz.promotion.service.impl;

import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupUserService;
import cn.jdcloud.medicine.mall.dao.promotion.GroupUserMapper;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUser;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUserResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

}
