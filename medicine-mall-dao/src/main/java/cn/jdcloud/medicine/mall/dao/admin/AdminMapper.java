package cn.jdcloud.medicine.mall.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.admin.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin>{

    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> listAll(@Param("admin") Admin queryAdmin);

    int updateLastLoginTime(@Param("adminId") Integer adminId);

    Admin getByAccount(@Param("account") String account);

    void updateStatus(@Param("adminId") Integer adminId, @Param("status") byte status);

    void updatePassword(Admin admin);

    void deleteByMobile(String mobile);

    Admin selectByMobile(String mobile);
}