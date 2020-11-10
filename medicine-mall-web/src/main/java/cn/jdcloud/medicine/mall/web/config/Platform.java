package cn.jdcloud.medicine.mall.web.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class Platform implements Serializable{

    private static final long serialVersionUID = -5623531485L;

    //平台名称
    private String name;
    //平台logo
    private String logo;

}
