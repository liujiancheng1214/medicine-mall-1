package cn.jdcloud.medicine.mall.api.biz.weixin.dto;


import lombok.Data;

@Data
public class WeixinAppInfoDto {
    private String code;
    private String iv;
    private String  encryptedData;
}
