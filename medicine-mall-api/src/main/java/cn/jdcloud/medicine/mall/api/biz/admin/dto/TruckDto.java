package cn.jdcloud.medicine.mall.api.biz.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * @author qun.xu
 * @desc   admindto
 * @date   2018.09.17
 */
@Data
public class TruckDto {


    private Integer adminId;

    private String name;

    private String account;

    private String mobile;

    private String password;

    private String oldPassword;

    private String newPassword1;

    private String newPassword2;

    private List<Integer> adminRoles;

}
