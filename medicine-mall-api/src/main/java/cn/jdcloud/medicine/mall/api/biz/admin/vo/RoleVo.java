package cn.jdcloud.medicine.mall.api.biz.admin.vo;
import cn.jdcloud.medicine.mall.domain.admin.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleVo {

    private Integer id;

    private String roleName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date updateTime;

    public RoleVo(){}
    public RoleVo(Role role){
        this.id = role.getId();
        this.roleName = role.getRoleName();
        this.createTime = role.getCreateTime();
        this.updateTime = role.getUpdateTime();
    }



}
