package cn.jdcloud.medicine.mall.domain.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author chenQF
 * @desc 用户证件照片
 * @date 2020/8/27 0027 10:44
 */
@Data
@TableName("t_user_img")
public class UserImg {

    private Integer id;
    private Integer userId; //用户id
    private String imgUrl; //图片地址
    private String imgRemark; //图片描述、类型
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

}
