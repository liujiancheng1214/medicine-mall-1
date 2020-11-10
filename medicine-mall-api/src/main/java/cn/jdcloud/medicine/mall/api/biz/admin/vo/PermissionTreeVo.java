package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import cn.jdcloud.medicine.mall.domain.admin.Permission;
import lombok.Data;

import java.util.List;

@Data
public class PermissionTreeVo {

    private Integer parentId;

    private Integer value;

    private String code;

    private String title;

    private List<PermissionTreeVo> children;

    public PermissionTreeVo() {}

    public PermissionTreeVo(Permission perm) {
        this.parentId = perm.getParentId();
        this.value = perm.getId();
        this.code = perm.getValue();
        this.title = perm.getName();
    }
}