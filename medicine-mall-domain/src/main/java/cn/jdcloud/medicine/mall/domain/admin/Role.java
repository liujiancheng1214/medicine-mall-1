package cn.jdcloud.medicine.mall.domain.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;

@Data
@TableName("s_role")
public class Role extends BaseDomain {

    private Integer id;

    private String roleName;

    @TableField(exist = false)
    private Byte isDeleted;

}