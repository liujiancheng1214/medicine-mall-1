package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserDescService;
import cn.jdcloud.medicine.mall.dao.user.UserDescMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.user.User;
import cn.jdcloud.medicine.mall.domain.user.UserDesc;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenQF
 * @desc 用户备注业务层实现
 * @date 2020/8/27 0027 10:44
 */
@Service
@Transactional
public class UserDescServiceImpl extends ServiceImpl<UserDescMapper, UserDesc> implements UserDescService {

    @Resource
    UserDescMapper userDescMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<UserDesc> getAllUserDesc(Integer userId) {
        Map map = new HashMap();
        map.put("user_id",userId);
        List<UserDesc> list = userDescMapper.selectByMap(map);
        return list;
    }

    @Override
    public void add(UserDesc userDesc) {
        userDesc.setCreateTime(new Date());
        int count = userDescMapper.insert(userDesc);
        if(count <= 0){
            throw new ApiException(1000,"添加备注失败");
        }
        //开户状态 0已开户 1未开户
        User user = new User();
        user.setId(userDesc.getUserId());
        user.setAccountStatus(userDesc.getType());
        int num = userMapper.updateById(user);
        if(num <= 0){
            throw new ApiException(1000,"添加备注失败，修改用户开户状态失败");
        }
    }

}
