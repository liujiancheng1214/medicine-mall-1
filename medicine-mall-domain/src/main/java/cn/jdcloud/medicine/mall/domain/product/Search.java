package cn.jdcloud.medicine.mall.domain.product;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_search")
public class Search {
    private Integer id;

    private Integer userId;

    private String searchValue;

    private Date createTime;
}
