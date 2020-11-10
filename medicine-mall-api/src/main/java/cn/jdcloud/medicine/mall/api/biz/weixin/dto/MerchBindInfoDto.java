package cn.jdcloud.medicine.mall.api.biz.weixin.dto;


import lombok.Data;

@Data
public class MerchBindInfoDto {
    //shopid关联code
    private String code;
    private String openId;
    private String headImg;
    private String nickName;
    private String mobile;
    private byte isChange;
    private String unionId;
//    private Long expireTime;
}
