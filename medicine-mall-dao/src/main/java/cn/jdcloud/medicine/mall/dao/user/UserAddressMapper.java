package cn.jdcloud.medicine.mall.dao.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.user.UserAddress;

import java.util.List;

public interface UserAddressMapper extends BaseMapper<UserAddress>{

    /**
     * 获取当前用户的所有地址
     * @param userId
     * @return
     */
    List<UserAddress> listByUserId(Integer userId);

    /**
     * 逻辑删除用户地址
     * @param addressId
     * @return
     */
    boolean deleteByAddressId(Integer addressId);

    /**
     * 通过id获取地址
     * @param addressId
     * @return
     */
    UserAddress selectByAddressId(Integer addressId);
}