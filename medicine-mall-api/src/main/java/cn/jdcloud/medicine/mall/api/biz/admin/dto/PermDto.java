package cn.jdcloud.medicine.mall.api.biz.admin.dto;

import cn.jdcloud.medicine.mall.domain.admin.Permission;
import lombok.Data;


@Data
public class PermDto {

    private Integer id;

    private Integer parentId;

    private String name;

    private String value;

    public Permission createPermission(){
        Permission permission = new Permission();
        permission.setId(this.id);
        permission.setParentId(this.parentId);
        permission.setName(this.name);
        permission.setValue(this.value);
        return permission;
    }
}
