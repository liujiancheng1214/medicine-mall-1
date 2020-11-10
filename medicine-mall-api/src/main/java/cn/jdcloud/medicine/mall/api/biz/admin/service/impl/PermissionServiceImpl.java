package cn.jdcloud.medicine.mall.api.biz.admin.service.impl;

import cn.jdcloud.medicine.mall.api.biz.admin.service.PermissionService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.PermissionTreeVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.PermissionVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.jdcloud.medicine.mall.dao.admin.PermissionMapper;
import cn.jdcloud.medicine.mall.domain.admin.Permission;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public Set<String> findPermissionValusByRoleIds(Set<Integer> roleIds) {
        Set<String> perIds = new HashSet<>();
        if(CollectionUtils.isEmpty(roleIds)){
            return perIds;
        }
        List<Permission> list = permissionMapper.listPermsByRoles(roleIds);
        if(list==null){
            return perIds;
        }
        for (Permission p:list) {
            perIds.add(p.getValue());
        }
        return perIds;
    }

    @Override
    public List<PermissionTreeVo> getTree() {
        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<>());
        List<PermissionTreeVo> vo = permissions.stream().map(perm -> new PermissionTreeVo(perm)).collect(Collectors.toList());
        Map<Integer, List<PermissionTreeVo>> permMap = vo.stream().collect(Collectors.groupingBy(PermissionTreeVo::getParentId, Collectors.toList()));
        List<PermissionTreeVo> vos = permMap.get(0);
        if (vos==null){
            vos = new ArrayList<>();
        }
        this.initChildren(vos,permMap);
        return vos;
    }

    @Override
    public Page<PermissionVo> page(Page<Permission> page, QueryWrapper<Permission> queryWrapper) {
        Page<Permission> permPage = permissionMapper.selectPage(page, queryWrapper);
        Page<PermissionVo> permVoPage = new Page<>(permPage.getCurrent(), permPage.getSize(), permPage.getTotal());
        List<PermissionVo> records = new ArrayList<>();
        permPage.getRecords().forEach(role -> records.add(new PermissionVo(role)));
        permVoPage.setRecords(records);
        return permVoPage;
    }

    private void initChildren(List<PermissionTreeVo> vos, Map<Integer, List<PermissionTreeVo>> map){
        vos.forEach(vo -> {
            List<PermissionTreeVo> childrens = map.get(vo.getValue());
            if(childrens!=null){
                vo.setChildren(childrens);
                initChildren(childrens,map);
            }
        });
    }
}
