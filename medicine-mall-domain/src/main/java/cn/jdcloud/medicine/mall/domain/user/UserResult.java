package cn.jdcloud.medicine.mall.domain.user;

import cn.jdcloud.medicine.mall.domain.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
/**
 * @desc 查询用户结果类
 */
public class UserResult extends User {

    private String provinceName;

    private String cityName;

    private String districtName;

    private String userLevelName;

}
