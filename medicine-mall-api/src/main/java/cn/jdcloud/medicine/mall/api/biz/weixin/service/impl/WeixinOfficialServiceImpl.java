package cn.jdcloud.medicine.mall.api.biz.weixin.service.impl;

import cn.jdcloud.medicine.mall.api.biz.weixin.service.WeixinOfficialService;
import com.google.gson.Gson;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.utils.HttpUtil;
import cn.jdcloud.medicine.mall.api.biz.weixin.bean.WeiXinUserInfoBean;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.AccessToken;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.Open;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.RequestUrlSign;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.Ticket;
import cn.jdcloud.medicine.mall.api.biz.weixin.vo.WeiXinUserInfoVo;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WeixinOfficialServiceImpl implements WeixinOfficialService {

    public static final String REDIS_WEIXIN_PREFIX="weixin:";
    public static final String WEIXIN_OFFICIAL_ACCESS_TOKEN=REDIS_WEIXIN_PREFIX+"official:accesstoken";
    public static final String WEIXIN_OFFICIAL_SHARE_TICKET=REDIS_WEIXIN_PREFIX+"official:ticket";

    @Resource Gson gson;
    @Resource(name="jdkTemplate")
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @return 微信公众号AccessToken
     * @author qun.xu
     * @desc 获取微信公众号的token，相关参数在token中.保存在redis缓存中
     */
    @Override
    public String  getAccessToken(String fromAccount) {
        return getOfficialToken(fromAccount);
    }
    /**
     * @return 微信公众号Ticket
     * @author qun.xu
     * @desc 根据accessToken获取tiket保存在redis缓存中
     */
    @Override
    public String  getTicket(String fromAccount,String accessToken) {
        return getOfficialTicket(fromAccount,accessToken);
    }

    /**
     * @return 微信公众号AccessToken
     * @author qun.xu
     * @desc 根据ticket 获取链接签名
     */
    @Override
    public RequestUrlSign getShareUrlSign(String shareUrl, String fromAccount) throws Exception {
         String token =  getAccessToken( fromAccount);
         String ticket = getTicket(fromAccount,token);
         RequestUrlSign  sign = sign(fromAccount,ticket,shareUrl);
         return sign;
    }

    /**
     * @param code
     * @return
     */
    @Override
    public Open getOpenObject(String code){
        String officeAppId = WeixinConstant.APP_ID;
        String officeAppSecret = WeixinConstant.APP_SECRET;
        StringBuilder url = new StringBuilder(WeixinConstant.OAUTH2_URL);
        url.append("?appid=").append(officeAppId)
                .append("&secret=").append(officeAppSecret)
                .append("&code=").append(code)
                .append("&grant_type=").append("authorization_code");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        CloseableHttpResponse response =null;
        String param = "";
        logger.info("get openid by code url:" + url);
        Open open = new Open();
        try {
            response = client.execute(get);
            param = HttpUtil.getResponseBodyAsString(response);
            open = gson.fromJson(param, Open.class);
            logger.info("get openid by code result param:" + param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(SysCodeEnums.PARAMS_ERROR);
        }
        return open;
    }
    /**
     * 请求路径签名
     * @param ticket
     * @param url
     * @return
     * @throws Exception
     */
    private RequestUrlSign sign(String fromAccount,String ticket, String url) throws  Exception {
        String nonce_str = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = "";
        String params  ="jsapi_ticket=" + ticket +
                 "&noncestr=" + nonce_str +
                 "&timestamp=" + timestamp +
                 "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(params.getBytes("UTF-8"));
        signature = byteToHex(crypt.digest());
        RequestUrlSign  requestUrlSign = new RequestUrlSign();
        requestUrlSign.setRequestUrl(url);
        requestUrlSign.setJsApiTicket(ticket);
        requestUrlSign.setNonceStr(nonce_str);
        requestUrlSign.setTimestamp(timestamp);
        requestUrlSign.setSignature(signature);
        String appId = WeixinConstant.APP_ID;
        requestUrlSign.setAppId(appId);
        return requestUrlSign;
    }


    private void saveRedisOfficialToken(String fromAccount, AccessToken accessToken){
        BoundValueOperations<String, String>
                bvo = redisTemplate.boundValueOps(WEIXIN_OFFICIAL_ACCESS_TOKEN+":"+fromAccount);
        bvo.set(accessToken.getAccess_token());
        bvo.expire(accessToken.getExpires_in()*1000, TimeUnit.MILLISECONDS);
    }

    private String  getRedisOfficialToken(String fromAccount){
        BoundValueOperations<String, String>
                bvo = redisTemplate.boundValueOps(WEIXIN_OFFICIAL_ACCESS_TOKEN+":"+fromAccount);
        String accessToken = bvo.get();
       return accessToken;
    }

    private void saveRedisOfficialTicket(String fromAccount, Ticket ticket){
        BoundValueOperations<String, String>
                bvo = redisTemplate.boundValueOps(WEIXIN_OFFICIAL_SHARE_TICKET+":"+fromAccount);
        bvo.set(ticket.getTicket());
        bvo.expire(ticket.getExpires_in()*1000, TimeUnit.MILLISECONDS);
    }

    private String  getRedisOfficialTicket(String fromAccount){
        BoundValueOperations<String, String>
                bvo = redisTemplate.boundValueOps(WEIXIN_OFFICIAL_SHARE_TICKET+":"+fromAccount);
        String ticket = bvo.get();
        return ticket;
    }


    /**
     *
     * @author qun.xu
     * @desc   请求公众号token
     * @date   2018.03.26
     */
    private String   getOfficialToken(String fromAccount){
        String officialToken  = getRedisOfficialToken(fromAccount);
        if(!StringUtils.isEmpty(officialToken)){
            return officialToken;
        }

        String appId = WeixinConstant.APP_ID;
        String appSecret = WeixinConstant.APP_SECRET;

        StringBuffer sb = new StringBuffer();
        sb.append(WeixinConstant.OFFICIAL_TOKEN_URL).append("?grant_type=client_credential")
                    .append("&appid=")
                    .append(appId)
                    .append("&secret=")
                    .append(appSecret);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(sb.toString());
        CloseableHttpResponse response =null;

        String responseBody = new String();
            try {
            response = client.execute(get);
            responseBody = HttpUtil.getResponseBodyAsString(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        AccessToken accessToken =  gson.fromJson(responseBody, AccessToken.class);
        try {
            //redis 保存
            saveRedisOfficialToken(fromAccount,accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            //如果保存失败,提示系统繁忙
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        return accessToken.getAccess_token();
    }


    /**
     * 请求公众号token
     */
    private String   getOfficialTicket(String fromAccount,String  accessToken){

        String officialTicket  = getRedisOfficialTicket(fromAccount);
        if(!StringUtils.isEmpty(officialTicket)){
            return officialTicket;
        }
        StringBuffer  sb = new StringBuffer();
        String ticketUrl  = sb.append(WeixinConstant.OFFICIAL_TICKET_URL).
                append("?access_token=").
                append(accessToken).
                append("&type=jsapi").toString();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet ticketUrlGet = new HttpGet(ticketUrl.toString());
        CloseableHttpResponse response;
        String responseBody  = new String();
        try {
            response = client.execute(ticketUrlGet);
            responseBody = HttpUtil.getResponseBodyAsString(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        Ticket ticket =  gson.fromJson(responseBody, Ticket.class);
        try {
            //redis 保存
            saveRedisOfficialTicket(fromAccount,ticket);
        } catch (Exception e) {
            e.printStackTrace();
            //如果保存失败,提示系统繁忙
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        return  ticket.getTicket();
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

    /**
     * @return
     * @author qun.xu
     * @desc   获取微信用户信息
     */
    @Override
    public WeiXinUserInfoVo getUserInfoByOpenId(String openId, String accessToken) {
        //{"access_token":"9_rML1xrMok4ee-1Lfb3ThwTLbKW8WVcNNwCvVA1YOm6pnT2yjSS0ISpeJjp6KyKvOKtlsW1S5o5gR-G4pyC8a-A","expires_in":7200,"refresh_token":"9_ZVkKTePRpvnKhdIcj6GTmRtTFcSUP-Sb8o7wQpMqPOa8advKHWzn4awQdxGtlnP_y8JLUUBxuEDTPAHyiBdAvg","openid":"oVLwC0vSdC0amTL-h3KrMPatW2N0","scope":"snsapi_userinfo","unionid":"oJ0BwwEqLhvIWcmiGJfzHQQ9MoUw"}
//         accessToken = "9_D_GgOvdiMWavcJFeV7VmW-1zroOc9i4-UXa6LWLKLRLK0mAxUurxFGdBQerhuXOKgMTNOsSS2rg6sKTuUtYkLQ";
//         openId = "oVLwC0vSdC0amTL-h3KrMPatW2N0";
        WeiXinUserInfoVo userInfoVo = new WeiXinUserInfoVo();
        StringBuilder url = new StringBuilder(WeixinConstant.GET_WEIXIN_USER_INFO);
        url.append("?access_token=").append(accessToken)
                .append("&openid=").append(openId)
                .append("&lang=").append("zh_CN");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        CloseableHttpResponse response =null;
        String reponseStr = "";
        try {
            response = client.execute(get);
            reponseStr = HttpUtil.getResponseBodyAsString(response);
            WeiXinUserInfoBean userInfoBean =  gson.fromJson(reponseStr, WeiXinUserInfoBean.class);
            WeiXinUserInfoVo userInfoVo1 = new WeiXinUserInfoVo();
            userInfoVo1.setHeadImgUrl(userInfoBean.getHeadimgurl());
            userInfoVo1.setNickName(userInfoBean.getNickname());
            userInfoVo1.setOpenId(userInfoBean.getOpenid());
            userInfoVo1.setSex(userInfoBean.getSex());
            return userInfoVo1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoVo;
    }


    /**
     * @return
     * @author qun.xu
     * @desc   获取微信用户信息
     */
    @Override
    public WeiXinUserInfoVo getUserInfoByOpen(Open open) {
        WeiXinUserInfoVo userInfoByOpenId =
                getUserInfoByOpenId(open.getOpenid(), open.getAccess_token());
        userInfoByOpenId.setUnionId(open.getUnionid());
        return userInfoByOpenId;
    }



//    public static void main(String[]args){
//        WeixinOfficialServiceImpl.getUserInfoByOpenId("","");
//    }
}
