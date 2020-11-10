package cn.jdcloud.medicine.mall.api.biz.admin.service;

import cn.jdcloud.medicine.mall.api.biz.admin.vo.PermissionTreeVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.PermissionVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.jdcloud.medicine.mall.domain.admin.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService extends IService<Permission> {

    Set<String> findPermissionValusByRoleIds(Set<Integer> roleIds);

    List<PermissionTreeVo> getTree();

    Page<PermissionVo> page(Page<Permission> page, QueryWrapper<Permission> queryWrapper);
}
