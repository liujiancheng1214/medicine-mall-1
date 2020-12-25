package cn.jdcloud.medicine.mall.api.biz.integral.service.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class IntegralVo {
	private Integer id;

    private Integer amount;

    private String reamrk;

    private String type;

    private Integer userId;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    private String typeDesc;

    private Integer changeType;
}
