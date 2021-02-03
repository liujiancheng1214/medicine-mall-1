package cn.jdcloud.medicine.mall.api.biz.share.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShareGroupUser {
	@ApiModelProperty("头像")
	private String headImg;  //用户头像
	@ApiModelProperty("名称")
	private String name; //客户名/公司名/证件名称
	@ApiModelProperty("时间")
	  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date  time;
}
