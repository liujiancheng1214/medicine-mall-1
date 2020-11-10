package cn.jdcloud.medicine.mall.domain.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;

@Data
@TableName("s_permission")
public class Permission extends BaseDomain {

    private Integer id;

    private Integer parentId;

    private String value;

    private String name;

    @TableField(exist = false)
    private Byte isDeleted;

}