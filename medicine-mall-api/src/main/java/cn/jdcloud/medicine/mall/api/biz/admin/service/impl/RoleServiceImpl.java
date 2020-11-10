package cn.jdcloud.medicine.mall.api.biz.admin.service.impl;

import cn.jdcloud.medicine.mall.api.biz.admin.dto.RoleDto;
import cn.jdcloud.medicine.mall.api.biz.admin.service.RoleService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.domain.admin.RolePermission;
import cn.jdcloud.medicine.mall.domain.product.Item;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.jdcloud.medicine.mall.dao.admin.RoleMapper;
import cn.jdcloud.medicine.mall.dao.admin.RolePermMapper;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService {

    @Resource
    RoleMapper roleMapper;
    @Resource
    RolePermMapper rolePermMapper;

    @Override
    public Set<Integer> findRolesByAdminId(Integer adminId) {
        List<Role> list = roleMapper.listRolesByAdminId(adminId);
        Set<Integer> set = new HashSet<>();
        if(!CollectionUtils.isEmpty(list)){
            for (Role role:list) {
                set.add(role.getId());
            }
        }
        return set;
    }

    @Override
    @Transactional
    public boolean saveOrUpdateRole(RoleDto roleDto) throws Exception {
        Role role = roleDto.createRole();
        boolean save = this.saveOrUpdate(role);
        if (save){
            Integer roleId = role.getId();
            rolePermMapper.deleteRolePerm(roleId);
            List<RolePermission> permissions = Arrays.stream(roleDto.getPerms())
                    .map(id -> new RolePermission(roleId, id))
                    .collect(Collectors.toList());
            rolePermMapper.insertBatch(permissions);
        }
        return save;
    }

    @Override
    public Page<RoleVo> page(Page<Role> page, QueryWrapper<Role> queryWrapper) {
        Page<Role> rolePage = roleMapper.selectPage(page, queryWrapper);
        Page<RoleVo> roleVoPage = new Page<>(rolePage.getCurrent(), rolePage.getSize(), rolePage.getTotal());
        List<RoleVo> records = new ArrayList<>();
        rolePage.getRecords().forEach(role -> records.add(new RoleVo(role)));
        roleVoPage.setRecords(records);
        return roleVoPage;
    }
}
