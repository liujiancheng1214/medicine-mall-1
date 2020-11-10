package cn.jdcloud.medicine.mall.api.biz.weixin.dto;


import lombok.Data;

@Data
public class WeixinEncryptedDataDto {
    private String openId;
    private String nickName;
    private byte gender;
    private String  language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String headImg;
}
