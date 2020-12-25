package cn.jdcloud.medicine.mall.api.biz.promotion.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.domain.promotion.GroupUser;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUserResult;

/**
 * @author chenQF
 * @desc 拼团参与记录接口
 * @date 2020/8/24 0024 13:53
 */
public interface GroupUserService extends IService<GroupUser> {
    /**
     * 查询拼团参与记录列表
     * @param page
     * @param groupId
     * @return
     */
    Page<GroupUserResult> listGroupUser(Page<GroupUserResult> page, String groupId);
    
   
    
    GroupUser queryGroupUserByUserIdAndOrderId(Integer userId,String orderId);

}
