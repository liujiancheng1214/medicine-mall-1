package cn.jdcloud.medicine.mall.api.biz.carousel.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CarouselVo {
	private Integer id;

	@ApiModelProperty(" 类型 0 直接跳连接 1 普通商品 2 团购商品 ")
    private Integer type;  // 类型 0 直接跳连接 1 普通商品 2 团购商品 
	@ApiModelProperty("type=0 直接存储连接, type=1 普通商品编码  type=2 活动编码 ")
    private String val;

    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    
    private String imageUrl;

    private String sort;
}
