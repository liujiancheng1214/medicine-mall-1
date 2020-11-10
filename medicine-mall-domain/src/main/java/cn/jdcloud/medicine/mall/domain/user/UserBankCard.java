package cn.jdcloud.medicine.mall.domain.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenQF
 * @desc 客户银行卡
 * @date 2020/8/26 0026 9:37
 */
@Data
@TableName("t_user_bank_card")
public class UserBankCard {

    private Integer id;
    private Integer userId;
    private String bankName; //银行名字
    private String bankCardNo; //银行卡号
    private String bankUserName; //开户人名字
    private String bankUserMobile; //开户人手机

}
