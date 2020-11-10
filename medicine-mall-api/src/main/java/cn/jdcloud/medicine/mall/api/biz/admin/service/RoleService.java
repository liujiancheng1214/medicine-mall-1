package cn.jdcloud.medicine.mall.api.biz.admin.service;

import cn.jdcloud.medicine.mall.api.biz.admin.dto.RoleDto;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import cn.jdcloud.medicine.mall.domain.product.Item;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface RoleService extends IService<Role> {

    Set<Integer> findRolesByAdminId(Integer adminId);

    boolean saveOrUpdateRole(RoleDto roleDto) throws Exception;

    public Page<RoleVo> page(Page<Role> page, QueryWrapper<Role> queryWrapper);
}
