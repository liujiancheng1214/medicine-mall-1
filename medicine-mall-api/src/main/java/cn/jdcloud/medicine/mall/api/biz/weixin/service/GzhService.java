package cn.jdcloud.medicine.mall.api.biz.weixin.service;

import cn.jdcloud.medicine.mall.api.biz.weixin.bo.UrlSign;
import cn.jdcloud.medicine.mall.api.biz.weixin.bo.WxAccessToken;
import cn.jdcloud.medicine.mall.api.biz.weixin.bo.Ticket;

public interface GzhService {

    String WEIXIN_ACCESS_TOKEN = "weixin:access_token:";
    String WEIXIN__TICKET = "weixin:ticket:";

    /**
     * 获取微信公众号access_token
     * @param appId
     * @return
     */
    WxAccessToken getToken(String appId);

    /**
     * 获取公众号jsApiTicket
     * @param appId
     * @param accessToken
     * @return
     */
    Ticket getTicket(String appId, String accessToken);


    /***
     * 对公众号页面签名
     * @param requestUrl
     * @param appId
     * @return
     */
    UrlSign getUrlSign(String appId, String requestUrl) throws Exception;
}
