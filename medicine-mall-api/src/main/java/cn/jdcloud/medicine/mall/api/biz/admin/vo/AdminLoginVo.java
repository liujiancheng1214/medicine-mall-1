package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import cn.jdcloud.medicine.mall.domain.admin.Admin;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * 这个类是参照JDBCRealm写的，主要是自定义了如何查询用户信息，如何查询用户的角色和权限，如何校验密码等逻辑
 */
@Data
public class AdminLoginVo{
    private String name;
    private String nickName;
    private String headImg;
    private Set<String>roles;
    private Set<String>perms;
    private String token;
    public AdminLoginVo(Admin admin){
        this.token = UUID.randomUUID().toString();
        this.name = admin.getName();
        this.nickName = admin.getName();
        this.headImg = admin.getHeadImg();
    }
}
