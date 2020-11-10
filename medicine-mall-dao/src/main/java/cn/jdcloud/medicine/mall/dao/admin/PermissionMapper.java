package cn.jdcloud.medicine.mall.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.admin.Permission;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> listPermsByRoles(@Param("list") Set<Integer> roleIds);

}