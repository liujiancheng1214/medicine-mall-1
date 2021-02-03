package cn.jdcloud.medicine.mall.domain.config;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_config")
public class Config {
    private Integer id;

    private String value;

    private String code;

    private String remark;
}