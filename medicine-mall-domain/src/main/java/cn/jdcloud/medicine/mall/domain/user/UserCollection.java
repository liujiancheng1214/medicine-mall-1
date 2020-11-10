package cn.jdcloud.medicine.mall.domain.user;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_user_collection")
public class UserCollection {
	private Integer id;

    private String itemNo;

    private Integer userId;

    private Date createTime;
}
