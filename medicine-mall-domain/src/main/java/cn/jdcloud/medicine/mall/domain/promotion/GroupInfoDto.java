package cn.jdcloud.medicine.mall.domain.promotion;

import lombok.Data;

/**
 * @author chenQF
 * @desc 拼团记录查询条件封装类
 * @date 2020/8/24 0024 14:01
 */
@Data
public class GroupInfoDto {

    private String startTime; //开始时间
    private String endTime; //结束时间
    private String status; //拼团状态
    private String promotionId; //活动编号
    private String keyWord; //发起人姓名或电话

}
