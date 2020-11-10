package cn.jdcloud.medicine.mall.domain.user;

import lombok.Data;

import java.util.Date;

/**
 * @author chenQF
 * @desc
 * @date 2020/8/17 0017 14:18
 */
@Data
public class UserExcel {
    private String helpCode; //客户编码

    private String mobile; //手机号码

    private String companyName; //客户名称

    private String contactName; //联系人名字

    private String contactPhone; //联系人手机

    private String contactAddress; //联系人地址

    private String type; //客户类型

    private String provinceId; //省份

    private String cityId; //城市

    private String districtId; //区县

    private String userLevelId; //客户等级

    private String taxNo; //税号

    private String invoiceType; //发票类型

    private String status; //客户状态

    private Date createTime; //注册时间

    private Date updateTime; //更新时间
}
