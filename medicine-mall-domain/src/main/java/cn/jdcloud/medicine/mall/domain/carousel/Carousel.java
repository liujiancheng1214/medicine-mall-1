package cn.jdcloud.medicine.mall.domain.carousel;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_carousel")
public class Carousel {
	
	    private Integer id;

	    private Integer type;  // 类型 0 直接跳连接 1 普通商品 2 团购商品 

	    private String val;

	    private String remark;

	    private Integer state; // 0 无效  1 有效 

	    private Date createTime;
	    
	    private String imageUrl;

	    private String sort;

}
