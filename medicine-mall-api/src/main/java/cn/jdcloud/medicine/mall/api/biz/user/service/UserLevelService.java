package cn.jdcloud.medicine.mall.api.biz.user.service;

import cn.jdcloud.medicine.mall.domain.user.UserLevel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author chenQF
 * @desc 后台用户等级接口层
 * @date 2020/8/14 0014 16:25
 */
public interface UserLevelService extends IService<UserLevel> {

    /**
     * 查询等级列表
     * @param page
     * @return
     */
    Page<UserLevel> listLevel(Page page);

    /**
     * 查询所有等级（下拉框）
     * @return
     */
    List<Map> listAllLevel();

    /**
     * 新增等级
     * @param userLevel
     */
    void saveUserLevel(UserLevel userLevel);

    /**
     * 更新等级
     * @param userLevel
     */
    void updateUserLevel(UserLevel userLevel);

    /**
     * 删除等级
     * @param id
     */
    void deleteUserLevel(Integer id);
}
