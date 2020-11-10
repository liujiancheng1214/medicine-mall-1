package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import cn.jdcloud.medicine.mall.domain.admin.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AdminVo  {

    public static final byte  STATUS_VALID = 0;
    public static final byte  STATUS_FREEZE = 2;

    private Integer id;

    private String account;

    private String mobile;

    private String name;

    private String password;

    private String headImg;

    private byte status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastLoginTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public AdminVo() {
    }

    public AdminVo(Admin admin) {
        this.id = admin.getId();
        this.account = admin.getAccount();
        this.mobile = admin.getMobile();
        this.name = admin.getName();
        this.password = admin.getPassword();
        this.headImg = admin.getHeadImg();
        this.status = admin.getStatus();
        this.lastLoginTime = admin.getLastLoginTime();
        this.createTime = admin.getCreateTime();
        this.updateTime = admin.getUpdateTime();
    }
}
