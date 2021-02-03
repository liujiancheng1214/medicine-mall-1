package cn.jdcloud.medicine.mall.domain.share;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_share_group")
public class ShareGroup {
    private Integer id;

    private Integer shareId;

    private Integer userId;

    private Integer conponId;

    private Date createTime;
}