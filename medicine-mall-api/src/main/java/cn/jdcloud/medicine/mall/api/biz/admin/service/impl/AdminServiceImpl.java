package cn.jdcloud.medicine.mall.api.biz.admin.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.framework.utils.security.MD5Util;
import cn.jdcloud.medicine.mall.api.biz.admin.code.AdminCode;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.AdminDto;
import cn.jdcloud.medicine.mall.api.biz.admin.service.AdminService;
import cn.jdcloud.medicine.mall.api.biz.admin.service.RoleService;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminRolesVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo;
import cn.jdcloud.medicine.mall.api.biz.user.utils.EncryptUtils;
import cn.jdcloud.medicine.mall.dao.admin.AdminMapper;
import cn.jdcloud.medicine.mall.dao.admin.AdminRoleMapper;
import cn.jdcloud.medicine.mall.dao.admin.RoleMapper;
import cn.jdcloud.medicine.mall.domain.admin.Admin;
import cn.jdcloud.medicine.mall.domain.admin.AdminRole;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qun.xu,qun.xu
 * @desc   后台管理员的服务接口实现
 * @date   2018/9/12 14:18
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    AdminMapper adminDao;
    @Resource
    AdminRoleMapper adminRoleDao;
    @Resource
    RoleMapper roleDao;
    @Resource
    RoleService roleService;

    public static final String DEFAULT_IMG = "";

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @author qun.xu
     * @desc   保存Admin
     * @date   2018.09.17
     */
    @Override
    @Transactional
    public void saveAdmin(AdminDto adminDto){
        if(adminDto.getAdminId()==null||adminDto.getAdminId()==0){
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",adminDto.getAccount());
            queryWrapper.or();
            queryWrapper.eq("mobile",adminDto.getMobile());
            queryWrapper.last("limit 1");
            Admin admin = this.getOne(queryWrapper);
            if(admin!=null){
                throw new ApiException(AdminCode.ADMIN_IS_EXIST);
            }
            admin = new Admin();
            BeanUtils.copyProperties(adminDto, admin);
            //保存默认密码为888888
            admin.setPassword(EncryptUtils.encryptPassword("888888"));
            admin.setMobile(adminDto.getMobile());
            admin.setHeadImg("http://47.97.108.207/images/931b2b3da5f7d763457a7c05db2d1c90.png");
            adminDao.insert(admin);
        }else{
            Admin  admin = new Admin();
            admin.setId(adminDto.getAdminId());
            admin.setAccount(adminDto.getAccount());
            admin.setName(adminDto.getName());
            admin.setMobile(adminDto.getMobile());
            adminDao.updateByPrimaryKey(admin);
        }
    }

    /**
     * @author qun.xu
     * @desc  重置密码
     * @param adminDto
     */
    @Override
    @Transactional
    public void restPassword(AdminDto adminDto){
        Admin admin =   adminDao.selectByPrimaryKey(adminDto.getAdminId());
        if(admin==null){
            throw new ApiException(AdminCode.ADMIN_IS_NOT_EXIST);
        }
        //保存默认密码为888888
        admin.setPassword(EncryptUtils.encryptPassword("888888"));
        adminDao.updatePassword(admin);
    }
    @Override
    public  AdminVo getAdminInfo(Integer adminId){
        Admin admin =  adminDao.selectByPrimaryKey(adminId);
        AdminVo adminVo = new AdminVo();
        adminVo.setName(admin.getName());
        adminVo.setAccount(admin.getAccount());
        adminVo.setMobile(admin.getMobile());
        List<AdminRole> adminRoleList =  adminRoleDao.listAdminRoles(admin.getId());
        //  adminVo.setRoles(roleCodeList);
        adminVo.setHeadImg(admin.getHeadImg());
        return adminVo;
    }

    @Override
    @Transactional
    public void  updatePassword(AdminDto adminDto){
        Admin admin = adminDao.selectByPrimaryKey(adminDto.getAdminId());
        if(!admin.getPassword().equals(MD5Util.encrypt(adminDto.getOldPassword()))){
            throw new ApiException(AdminCode.PASSWORD_OLD_IS_ERROR);
        }
        if(adminDto.getNewPassword1().equals(adminDto.getOldPassword())){
            throw new ApiException(AdminCode.PASSWORD_OLD_NEW_SAME);
        }
        if(!adminDto.getNewPassword1().equals(adminDto.getNewPassword2())){
            throw new ApiException(AdminCode.NEWPASSWORD_ISNOT_SAME);
        }
        admin.setId(adminDto.getAdminId());
        String encryptPassword = MD5Util.encrypt(adminDto.getNewPassword2());
        admin.setPassword(encryptPassword);
        adminDao.updatePassword(admin);
    }

    /**
     * @author qun.xu
     * @desc  获取所有角色和用户的角色列表
     * @param
     */
    @Override
    public AdminRolesVo getAdminRoles(Integer adminId){
        //获取用户的角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id","SELECT role_id FROM s_admin_role WHERE admin_id = '"+adminId+"'");
        AdminRolesVo vo = new AdminRolesVo();
        vo.setAdminId(adminId);
        List<Role> roleList = roleService.list(queryWrapper);
        // List<Role> 转换成 List<RoleVo>
        vo.setAdminRoles(this.roleToVo(roleList));
        List<Role> allRoleList = roleService.list();
        if (!CollectionUtils.isEmpty(allRoleList)) {
            // List<Role> 转换成 List<RoleVo>
            vo.setAllRoles(this.roleToVo(allRoleList));
        }
        return vo;
//        List<AdminRole> adminRoleList = adminRoleDao.listAdminRoles(adminId);
//        List<Integer> roleIds =  adminRoleList.stream().map(AdminRole::getRoleId).collect(Collectors.toList());
//        //获取所有的角色
//        List<Role> roleList =  roleDao.listAll();
//        AdminRolesVo adminRoleVo = new AdminRolesVo();
//        List<AdminRolesVo.AllRoleVo> allRoleVoList = Lists.newArrayList();
//        roleList.forEach(role -> {
//            AdminRolesVo.AllRoleVo allRoleVo =new AdminRolesVo.AllRoleVo();
//            allRoleVo.setKey(role.getId());
//            allRoleVo.setLabel(role.getRoleName());
//            allRoleVoList.add(allRoleVo);
//        });
//        adminRoleVo.setAllRoles(allRoleVoList);
//        adminRoleVo.setAdminRoles(roleIds);
//        return adminRoleVo;
    }

    /**
     * @author qun.xu
     * @desc  获取所有角色和用户的角色列表
     * @param adminDto
     */
    @Override
    @Transactional
    public void  saveAdminRoles(AdminDto adminDto){
        Integer adminId =adminDto.getAdminId();
        List<Integer> adminRoleIds = adminDto.getAdminRoles();
        //删除用户的角色
        adminRoleDao.deleteByAdminId(adminId);
        //新增用户的角色
        List<AdminRole> adminRoleList = Lists.newArrayList();
        adminRoleIds.forEach(roleId->{
                    AdminRole adminRole = new AdminRole(adminId,roleId);
                    adminRoleList.add(adminRole);
                }
        );
        adminRoleDao.insertBatch(adminRoleList);
    }

    @Override
    public Page<AdminVo> listAdmin(Page page, String search) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(search)){
            queryWrapper.like("name",search);
            queryWrapper.or();
            queryWrapper.like("account",search);
            queryWrapper.or();
            queryWrapper.like("mobile",search);
        }
        page = this.page(page,queryWrapper);
        List<AdminVo> adminVos = new ArrayList<>();
        for (Object o :page.getRecords()){
            adminVos.add(new AdminVo((Admin) o));
        }
        page.setRecords(adminVos);
        return page;
    }

    @Override
    @Transactional
    public void freezeAdmin(AdminDto adminDto) {
        adminDao.updateStatus(adminDto.getAdminId(), Admin.STATUS_FREEZE);
    }

    @Override
    @Transactional
    public void unfreezeAdmin(AdminDto adminDto) {
        adminDao.updateStatus(adminDto.getAdminId(), Admin.STATUS_VALID);
    }

    /**
     * List<Role> 转换成 List<RoleVo>
     *
     * @param roleList
     * @return java.util.List<cn.jdcloud.medicine.mall.api.biz.admin.vo.RoleVo>
     * @author HuZhengYu
     * @date 11:39 2020/8/20
     */
    private List<RoleVo> roleToVo(List<Role> roleList) {
        return roleList.stream().map(RoleVo::new).collect(Collectors.toList());
    }

}
