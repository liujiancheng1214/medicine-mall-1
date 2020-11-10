package cn.jdcloud.medicine.mall.domain.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author chenQF
 * @desc 拼团参与表
 * @date 2020/8/24 0024 15:36
 */
@Data
@TableName("t_group_user")
public class GroupUser {
    private Integer id;
    private Integer groupId; //拼团id
    private Integer userId; //参团人id
    private Date createTime; //创建时间
    private String orderId; //订单Id
    private Integer isPay; //1未支付 2支付锁定 3已支付
}
