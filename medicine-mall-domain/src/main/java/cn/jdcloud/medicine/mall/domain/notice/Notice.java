package cn.jdcloud.medicine.mall.domain.notice;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_notice")
public class Notice {
	private Integer id;

    private String title;

    private Integer isDelete;

    private String content;

    private Date createTime;
}
