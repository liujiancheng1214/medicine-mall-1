package cn.jdcloud.medicine.mall.domain.share;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_share")
public class Share {
    private Integer id;

    private Integer sponsorId;

    private Integer conponId;

    private Date createTime;

    
}