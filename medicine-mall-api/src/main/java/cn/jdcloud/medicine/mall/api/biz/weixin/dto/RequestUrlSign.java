package cn.jdcloud.medicine.mall.api.biz.weixin.dto;

import lombok.Data;

/**
 * Copyright (c) 2018, All Rights Reserved By (.....).
 * ver        date      	 author
 * ──────────────────────────────────
 * 1.0	    2018/3/15        qun.xu
 *
 * @描述:    微信链接签名
 */
@Data
public class RequestUrlSign {


    private String requestUrl;

    private String  jsApiTicket;

    private String nonceStr;

    private String  timestamp;

    private String signature;

    private String appId;
}
