package cn.jdcloud.medicine.mall.api.biz.weixin.dto;


import lombok.Data;

import java.util.Date;

@Data
public class WeixinPhoneDto {

    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private WaterMark watermark;

    class WaterMark{
        private String appid;
        private Date timestamp;
    }

}
