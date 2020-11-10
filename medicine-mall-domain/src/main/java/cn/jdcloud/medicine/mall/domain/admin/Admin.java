package cn.jdcloud.medicine.mall.domain.admin;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;



@Data
@EqualsAndHashCode(callSuper=false)
@TableName("s_admin")
public class Admin extends BaseDomain {

    public static final byte  STATUS_VALID = 0;
    public static final byte  STATUS_FREEZE = 2;

    private Integer id;

    private String account;

    private String mobile;

    private String name;

    private String password;

    private String headImg;

    private byte status;

    private Date lastLoginTime;

    @TableField(exist = false)
    private Byte isDeleted;



}