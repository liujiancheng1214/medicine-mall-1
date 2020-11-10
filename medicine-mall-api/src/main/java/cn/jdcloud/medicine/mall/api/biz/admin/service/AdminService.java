package cn.jdcloud.medicine.mall.api.biz.admin.service;

import cn.jdcloud.medicine.mall.api.biz.admin.dto.AdminDto;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminRolesVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.AdminVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.jdcloud.medicine.mall.domain.admin.Admin;

/**
 * @desc
 * 管理员业务
 */
public interface AdminService extends IService<Admin> {

     void saveAdmin(AdminDto adminDto);

     void restPassword(AdminDto adminDto);

     AdminVo getAdminInfo(Integer adminId);

     void updatePassword(AdminDto adminDto);

     AdminRolesVo getAdminRoles(Integer adminId);

     void  saveAdminRoles(AdminDto adminDto);

     Page<AdminVo> listAdmin(Page page, String search);

     void freezeAdmin(AdminDto adminDto);

     void unfreezeAdmin(AdminDto adminDto);
}
