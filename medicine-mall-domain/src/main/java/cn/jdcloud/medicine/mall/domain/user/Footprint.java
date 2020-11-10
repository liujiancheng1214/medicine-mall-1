package cn.jdcloud.medicine.mall.domain.user;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_footprint")
public class Footprint {
	    private Integer id;

	    private Integer userId;

	    private String itemNo;

	    private Date createTime;
}
