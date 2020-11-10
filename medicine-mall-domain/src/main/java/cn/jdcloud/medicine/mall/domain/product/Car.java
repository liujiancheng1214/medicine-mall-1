package cn.jdcloud.medicine.mall.domain.product;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_car")
public class Car {
    private Integer id;

    private Integer userId;

    private String itemNo;

    private String sku;
    
    private String batchNo;
    
    private Date createTime;

    private Date updateTime;

    private Integer num;

    private Integer isDelete;

    private Integer status;

    private Integer orderId;

   
}