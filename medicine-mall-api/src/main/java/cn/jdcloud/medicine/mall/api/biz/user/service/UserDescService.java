package cn.jdcloud.medicine.mall.api.biz.user.service;

import cn.jdcloud.medicine.mall.domain.user.UserDesc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @desc 用户备注业务接口
 */
public interface UserDescService extends IService<UserDesc>{

    /**
     * 查询用户备注
     * @param userId
     * @return
     */
    List<UserDesc> getAllUserDesc(Integer userId);

    /**
     * 添加备注
     * @param userDesc
     */
    void add(UserDesc userDesc);
}
