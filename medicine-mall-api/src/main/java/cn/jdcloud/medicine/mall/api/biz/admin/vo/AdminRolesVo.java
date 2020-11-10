package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import lombok.Data;

import java.util.List;

@Data
public class AdminRolesVo {

    private Integer adminId;

    private List<RoleVo> adminRoles;

    private List<RoleVo> allRoles;

}
