package cn.jdcloud.medicine.mall.domain.admin;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("s_role_permission")
public class RolePermission extends BaseDomain {

    private Integer id;

    private Integer permissionId;

    private Integer roleId;

    @TableField(exist = false)
    private Byte isDeleted;
    @TableField(exist = false)
    private Date updateTime;

    public RolePermission() {
    }

    public RolePermission(Integer roleId,Integer permissionId) {
        this.permissionId = permissionId;
        this.roleId = roleId;
    }
}