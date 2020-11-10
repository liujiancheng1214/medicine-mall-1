package cn.jdcloud.medicine.mall.api.biz.weixin.dto;

import lombok.Data;

/**
 */
@Data
public class WeixinLoginDto {

    private String mobile;

    private String nickName;

    private String headImgUrl;

    private String sex;

    private String smsCode;

    private byte regFrom;
}
