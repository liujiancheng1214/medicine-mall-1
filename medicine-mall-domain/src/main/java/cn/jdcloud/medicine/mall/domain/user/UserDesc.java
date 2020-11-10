package cn.jdcloud.medicine.mall.domain.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author chenQF
 * @desc 用户备注表
 * @date 2020/8/27 0027 16:10
 */
@Data
@TableName("t_user_desc")
public class UserDesc {

    private Integer id;
    private Integer userId;
    private Byte type; //类型 0开户，1拒绝开户
    private String content; //备注内容
    private Date createTime;

}
