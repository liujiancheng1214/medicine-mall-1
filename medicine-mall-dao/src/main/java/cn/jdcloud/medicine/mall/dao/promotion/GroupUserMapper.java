package cn.jdcloud.medicine.mall.dao.promotion;

import cn.jdcloud.medicine.mall.domain.promotion.GroupUser;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUserResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenQF
 * @desc 拼团记录数据访问层
 * @date 2020/8/24 0024 13:55
 */
public interface GroupUserMapper extends BaseMapper<GroupUser> {

    List<GroupUserResult> listGroupUser(@Param("page") Page<GroupUserResult> page, @Param("groupId") String groupId);

    Integer listCount(@Param("groupId") String groupId);
}
