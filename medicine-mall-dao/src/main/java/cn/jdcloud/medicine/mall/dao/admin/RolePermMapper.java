package cn.jdcloud.medicine.mall.dao.admin;

import cn.jdcloud.medicine.mall.domain.admin.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface RolePermMapper extends BaseMapper<RolePermission> {


    void insertBatch(@Param("list") List<RolePermission> rolePermList);

    void deleteRolePerm(@Param("roleId") Integer roleId);
}