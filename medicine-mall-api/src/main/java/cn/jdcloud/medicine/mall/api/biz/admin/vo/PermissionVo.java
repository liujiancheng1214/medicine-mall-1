package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import cn.jdcloud.medicine.mall.domain.admin.Permission;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PermissionVo {

    private Integer id;

    private Integer parentId;

    private String value;

    private String name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date createTime;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date updateTime;
    public PermissionVo() {
    }

    public PermissionVo(Permission permission) {
        this.id = permission.getId();
        this.parentId = permission.getParentId();
        this.value = permission.getValue();
        this.name = permission.getName();
        this.createTime = permission.getCreateTime();
        this.updateTime = permission.getUpdateTime();
    }
}