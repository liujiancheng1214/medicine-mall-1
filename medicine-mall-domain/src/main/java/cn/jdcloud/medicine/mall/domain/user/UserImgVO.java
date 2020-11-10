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
public class UserImgVO {
    private String imgUrl; //图片地址
    private String imgRemark; //图片描述、类型

    public UserImgVO() {
    }

    public UserImgVO(String imgUrl, String imgRemark) {
        this.imgUrl = imgUrl;
        this.imgRemark = imgRemark;
    }
}
