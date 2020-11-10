package cn.jdcloud.medicine.mall.domain.promotion;

import lombok.Data;

/**
 * @author chenQF
 * @desc 拼团返回结果
 * @date 2020/8/24 0024 13:50
 */
@Data
public class GroupInfoResult extends GroupInfo{

    private String promotionName; //活动名称
    private String plushUserName; //发起人姓名
    private String plushUserPhone; //发起人姓名

}
