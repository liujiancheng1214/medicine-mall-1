package cn.jdcloud.medicine.mall.domain.promotion;

import lombok.Data;

/**
 * @author chenQF
 * @desc 拼团参团记录返回结果
 * @date 2020/8/24 0024 15:37
 */
@Data
public class GroupUserResult extends GroupUser{

    private String userName; //参团人姓名
    private String userPhone; //参团人手机号

}
