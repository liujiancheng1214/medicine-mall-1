package cn.jdcloud.medicine.mall.dao.admin;

import cn.jdcloud.medicine.mall.domain.admin.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {

	List<Role> listRolesByAdminId(@Param("adminId") Integer adminId);

}
