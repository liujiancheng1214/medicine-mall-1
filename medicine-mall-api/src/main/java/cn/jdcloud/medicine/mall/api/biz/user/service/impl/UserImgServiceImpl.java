package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import cn.jdcloud.medicine.mall.api.biz.user.service.UserImgService;
import cn.jdcloud.medicine.mall.dao.user.UserImgMapper;
import cn.jdcloud.medicine.mall.domain.user.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chenQF
 * @desc 用户证件照片业务层实现
 * @date 2020/8/27 0027 10:44
 */
@Service
public class UserImgServiceImpl extends ServiceImpl<UserImgMapper, UserImg> implements UserImgService {

    @Resource
    UserImgMapper userImgMapper;

    @Override
    public List<UserImg> getAllUserImg(Integer userId) {
        Map map = new HashMap();
        map.put("user_id",userId);
        List<UserImg> userImgs = userImgMapper.selectByMap(map);
        return userImgs;
    }

}
