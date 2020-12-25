package cn.jdcloud.medicine.mall.domain.sige;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_sign")
public class Sign {
    private Integer id;

    private Integer userId;

    private String createTime;

    private Integer continuityDay;
}
