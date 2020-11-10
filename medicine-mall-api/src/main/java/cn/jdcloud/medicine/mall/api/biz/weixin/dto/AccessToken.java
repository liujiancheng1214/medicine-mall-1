package cn.jdcloud.medicine.mall.api.biz.weixin.dto;

import lombok.Data;

/**
 * @描述:  微信token
 */
@Data
public class AccessToken {

    private String access_token;

    private int expires_in;

    private Integer errcode;

    private String errmsg;
}
