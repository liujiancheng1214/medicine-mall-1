package cn.jdcloud.medicine.mall.api.biz.weixin.service.impl;

import cn.jdcloud.medicine.mall.api.biz.weixin.service.GzhService;
import com.google.gson.Gson;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.HttpUtil;
import cn.jdcloud.medicine.mall.api.biz.weixin.bo.Ticket;
import cn.jdcloud.medicine.mall.api.biz.weixin.bo.UrlSign;
import cn.jdcloud.medicine.mall.api.biz.weixin.bo.WxAccessToken;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("gzhService")
public class GzhServiceImpl implements GzhService {

    @Resource
    private Gson gson;

    @Resource
    private RedisTemplate redisTemplate;

    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    
    private static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";



    @Override
    public WxAccessToken getToken(String appId) {
        WxAccessToken token  = getRedisToken(appId);
        if(token!=null && !StringUtils.isEmpty(token.getAccessToken())){
            return token;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(TOKEN_URL).append("?grant_type=client_credential")
                .append("&appid=")
                .append(appId)
                .append("&secret=")
                .append(WeixinConstant.APP_SECRET);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(sb.toString());
        CloseableHttpResponse response =null;

        String responseBody = new String();
        try {
            response = client.execute(get);
            responseBody = HttpUtil.getResponseBodyAsString(response);
            token =  gson.fromJson(responseBody, WxAccessToken.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        try {
            //redis 保存
            saveRedisToken(appId,token);
        } catch (Exception e) {
            e.printStackTrace();
            //如果保存失败,提示系统繁忙
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        return token;
    }

    @Override
    public Ticket getTicket(String appId,String accessToken) {
        return getApiTicket(appId,accessToken);
    }
    /**
     * @return 微信公众号AccessToken
     * @author qun.xu
     * @desc 根据ticket 获取链接签名
     */
    @Override
    public UrlSign getUrlSign(String appId,String requestUrl) throws Exception {
        WxAccessToken token =  getToken(appId);
        Ticket ticket = getTicket(appId,token.getAccessToken());
        UrlSign  sign = sign(appId,ticket.getTicket(),requestUrl);
        return sign;
    }
    /**
     * 请求路径签名
     * @param ticket
     * @param url
     * @return
     * @throws Exception
     */
    private UrlSign sign(String appId, String ticket, String url) throws  Exception {
        String nonceStr = randomUUID();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = "";
        String params  ="jsapi_ticket=" + ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(params.getBytes("UTF-8"));
        signature = byteToHex(crypt.digest());
        UrlSign  urlSign = new UrlSign();
        urlSign.setRequestUrl(url);
        urlSign.setJsApiTicket(ticket);
        urlSign.setNonceStr(nonceStr);
        urlSign.setTimestamp(timestamp);
        urlSign.setSignature(signature);
        urlSign.setAppId(appId);
        return urlSign;
    }

    /**
     * 请求公众号token
     */
    private Ticket getApiTicket(String appId,String  accessToken){

        Ticket ticket  = getRedisTicket(appId);
        if(ticket!=null && !StringUtils.isEmpty(ticket.getTicket())){
            return ticket;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(TICKET_URL).
                append("?access_token=").
                append(accessToken).
                append("&type=jsapi").toString();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet ticketUrlGet = new HttpGet(sb.toString());
        CloseableHttpResponse response;
        String responseBody  = new String();
        try {
            response = client.execute(ticketUrlGet);
            responseBody = HttpUtil.getResponseBodyAsString(response);
            ticket =  gson.fromJson(responseBody, Ticket.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }

        try {
            //redis 保存
            saveRedisTicket(appId,ticket);
        } catch (Exception e) {
            e.printStackTrace();
            //如果保存失败,提示系统繁忙
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        return  ticket;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    private void saveRedisToken(String appId, WxAccessToken token){
        BoundValueOperations<String, WxAccessToken>
                bvo = redisTemplate.boundValueOps(WEIXIN_ACCESS_TOKEN+":"+appId);
        bvo.set(token);
        bvo.expire(token.getExpiresIn(), TimeUnit.SECONDS);
    }

    private WxAccessToken  getRedisToken(String appId){
        BoundValueOperations<String, WxAccessToken>
                bvo = redisTemplate.boundValueOps(WEIXIN_ACCESS_TOKEN+":"+appId);
        WxAccessToken accessToken = bvo.get();
        return accessToken;
    }

    private void saveRedisTicket(String appId, Ticket ticket){
        BoundValueOperations<String, Ticket>
                bvo = redisTemplate.boundValueOps(WEIXIN__TICKET+":"+appId);
        bvo.set(ticket);
        bvo.expire(ticket.getExpiresIn(), TimeUnit.SECONDS);
    }

    private Ticket getRedisTicket(String appId){
        BoundValueOperations<String, Ticket>
                bvo = redisTemplate.boundValueOps(WEIXIN__TICKET+":"+appId);
        Ticket ticket = bvo.get();
        return ticket;
    }

    private String randomUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
