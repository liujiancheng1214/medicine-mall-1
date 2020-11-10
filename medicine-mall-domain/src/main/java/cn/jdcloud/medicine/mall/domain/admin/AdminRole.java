package cn.jdcloud.medicine.mall.domain.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("s_admin_role")
public class AdminRole {

    private Integer id;

    private Integer adminId;

    private Integer roleId;

    private Date createTime;

    @TableField(exist = false)
    private Byte isDeleted;
    @TableField(exist = false)
    private Date updateTime;

    public AdminRole(){

    }

    public AdminRole( Integer adminId,Integer roleId){
            this.adminId =adminId;
            this.roleId = roleId;
    }

}