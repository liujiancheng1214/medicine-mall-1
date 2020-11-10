package cn.jdcloud.medicine.mall.api.biz.weixin.dto;

import lombok.Data;

/**
 * Copyright (c) 2018, All Rights Reserved By (.....).
 * ver        date      	 author
 * ──────────────────────────────────
 * 1.0	    2018/3/15        qun.xu
 *
 * @描述:  微信token
 */
@Data
public class Ticket {

    /**
     * {
     "errcode":0,
     "errmsg":"ok",
     "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
     "expires_in":7200
     }
      */
    private byte errcode;

    private String errmsg;

    private String ticket;

    private int expires_in;

}
