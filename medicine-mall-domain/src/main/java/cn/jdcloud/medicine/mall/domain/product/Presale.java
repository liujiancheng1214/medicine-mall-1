package cn.jdcloud.medicine.mall.domain.product;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data

@TableName("t_presale")
public class Presale {
	
	private Integer id;

    private Integer itemId;

    private String brandName;

    private String catogeryName;

    private Date sendDate;

    private Date createTime;
    
    private Integer limitNum;
}
