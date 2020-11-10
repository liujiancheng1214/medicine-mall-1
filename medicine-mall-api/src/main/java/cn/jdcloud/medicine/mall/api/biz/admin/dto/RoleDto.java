package cn.jdcloud.medicine.mall.api.biz.admin.dto;

import cn.jdcloud.medicine.mall.domain.admin.Role;
import lombok.Data;

/**
 * @author qun.xu
 * @desc   admindto
 * @date   2018.09.17
 */
@Data
public class RoleDto {
    private Integer id;

    private String roleName;

    private Integer[] perms;

    public Role createRole(){
        Role role = new Role();
        role.setId(this.id);
        role.setRoleName(this.roleName);
        return role;
    }
}
