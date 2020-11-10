package cn.jdcloud.medicine.mall.api.biz.weixin.bo;

import lombok.Data;

@Data
public class UrlSign {

    private String requestUrl;

    private String  jsApiTicket;

    private String nonceStr;

    private String  timestamp;

    private String signature;

    private String appId;
}
