package cn.jdcloud.medicine.mall.domain.user;

import lombok.Data;

@Data
/**
 * @desc 查询用户条件类
 */
public class UserDto {

    private String startRegisterTime;

    private String endRegisterTime;

    private String provinceId;

    private String cityId;

    private String districtId;

    private String userLevelId;

    private String mobile;

    private String accountStatus; //开户状态 0已开户 1未开户

    private String keyWord;

}
