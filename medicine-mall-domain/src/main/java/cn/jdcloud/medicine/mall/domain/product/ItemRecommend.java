package cn.jdcloud.medicine.mall.domain.product;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName(value = "t_item_recommend")
public class ItemRecommend {
    private Integer id;

    private String itemNo;

    private String recommendItemNo;

    private Integer sort;

    private Date createTime;
}