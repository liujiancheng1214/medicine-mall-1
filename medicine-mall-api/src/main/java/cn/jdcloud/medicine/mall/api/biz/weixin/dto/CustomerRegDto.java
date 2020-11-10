package cn.jdcloud.medicine.mall.api.biz.weixin.dto;


import lombok.Data;

@Data
public class CustomerRegDto {
    private String mobile;
    private String smsCode;
    private String openId;
    private String nickName;
    private String headImgUrl;
    private Byte sex;
    private Integer sellerId;
}
