package cn.jdcloud.medicine.mall.domain.sys;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("t_region")
public class Region {
    private Integer id;

    private String code;

    private String name;

    private String shortName;

    private Integer parentId;

    private Integer level;

    private Byte isCentral;

    private BigDecimal lng;

    private BigDecimal lat;

    private Integer sort;

    private String enName;

    private String enShortName;

   
}