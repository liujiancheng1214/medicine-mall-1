package cn.jdcloud.medicine.mall.api.biz.integral.service.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IntegralVo {
	private Integer id;
	@ApiModelProperty(value="积分数量")
    private Integer amount;
    @ApiModelProperty(value="积分类型")
    private String type;
    @ApiModelProperty(value="积分类型描述")
    private String typeDesc;
    private Integer userId;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date createTime;
    @ApiModelProperty(value="1 获得积分  0 使用积分")
    private Integer changeType;
    @ApiModelProperty(value="备注")
    private String reamrk;
}
