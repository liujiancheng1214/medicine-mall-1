package cn.jdcloud.medicine.mall.api.biz.notice.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class NoticeVo {
	private Integer id;

    private String title;

    private String content;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
}
