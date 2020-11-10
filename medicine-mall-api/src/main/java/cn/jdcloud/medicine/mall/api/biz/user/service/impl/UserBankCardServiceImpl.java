package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import cn.jdcloud.medicine.mall.api.biz.user.service.UserBankCardService;
import cn.jdcloud.medicine.mall.dao.user.UserBankCardMapper;
import cn.jdcloud.medicine.mall.domain.user.UserBankCard;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenQF
 * @desc 用户银行卡业务层
 * @date 2020/8/26 0026 9:44
 */
@Service
public class UserBankCardServiceImpl extends ServiceImpl<UserBankCardMapper, UserBankCard> implements
        UserBankCardService {

    @Resource
    UserBankCardMapper userBankCardMapper;

    @Override
    public List<UserBankCard> getByUserId(Integer userId) {
        Map map = new HashMap();
        map.put("user_id", userId);
        return userBankCardMapper.selectByMap(map);
    }
}
