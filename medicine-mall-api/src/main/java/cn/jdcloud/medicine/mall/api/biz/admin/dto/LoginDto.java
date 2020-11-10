package cn.jdcloud.medicine.mall.api.biz.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * 登陆传参
 */
@Data
public class LoginDto {

    private String account;

    private String password;

}
