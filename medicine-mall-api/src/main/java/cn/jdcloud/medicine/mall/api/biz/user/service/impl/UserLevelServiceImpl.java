package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserLevelService;
import cn.jdcloud.medicine.mall.dao.user.UserLevelMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.user.User;
import cn.jdcloud.medicine.mall.domain.user.UserLevel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenQF
 *  @desc 后台用户等级业务层
 * @date 2020/8/14 0014 16:26
 */
@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper,UserLevel> implements UserLevelService {

    @Resource
    UserLevelMapper userLevelMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public Page<UserLevel> listLevel(Page page) {
        return this.page(page,null);
    }

    @Override
    public List<Map> listAllLevel() {
        List<Map> mapList = new ArrayList<>();
        List<UserLevel> list = userLevelMapper.selectList(null);
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map.put("value",list.get(i).getId().toString());
            map.put("label",list.get(i).getLevelName());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void saveUserLevel(UserLevel userLevel) {
        userLevelMapper.insert(userLevel);
    }

    @Override
    public void updateUserLevel(UserLevel userLevel) {
        userLevelMapper.updateById(userLevel);
    }

    @Override
    public void deleteUserLevel(Integer id) {
        //判断该等级下面是否存在客户，存在则不能删除
        Map map = new HashMap();
        map.put("user_level_id",id);
        List<User> users = userMapper.selectByMap(map);
        if(users.size() > 0){
            throw new ApiException(UserCode.THERE_ARE_CUSTOMERS_UNDER_THE_LEVEL);
        }
        int count = userLevelMapper.deleteById(id);
        if(count <= 0){
            throw new ApiException(1000, "等级删除失败");
        }
    }
}
