package cn.jdcloud.medicine.mall.dao.admin;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.admin.AdminRole;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    int deleteByPrimaryKey(Integer id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    List<AdminRole> listAdminRoles(Integer adminId);

    void deleteByAdminId(Integer adminId);

    void insertBatch(List<AdminRole> adminRoleList);
}