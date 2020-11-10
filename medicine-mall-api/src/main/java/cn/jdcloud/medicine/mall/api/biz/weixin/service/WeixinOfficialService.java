package cn.jdcloud.medicine.mall.api.biz.weixin.service;



import cn.jdcloud.medicine.mall.api.biz.weixin.dto.Open;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.RequestUrlSign;
import cn.jdcloud.medicine.mall.api.biz.weixin.vo.WeiXinUserInfoVo;

/**
 * @描述: WeixinOfficialService  微信公众号服务接口
 */
public interface WeixinOfficialService {

    /**
     * @return 微信公众号AccessToken
     * @desc   获取微信公众号的token，相关参数在token中.保存在redis缓存中
     */
     String  getAccessToken(String fromAccount);


    /**
     * @param code
     * @return
     */
    Open  getOpenObject(String code);



    /**
     * @return 微信公众号AccessToken
     * @desc   根据accessToken获取tiket保存在redis缓存中
     */
    String getTicket(String fromAccount, String token);


    /**
     * @return 微信公众号AccessToken
     * @desc 根据ticket 获取链接签名
     */
     RequestUrlSign getShareUrlSign(String shareUrl, String fromAccount) throws Exception ;

    /**
     * @return
     * @desc   获取微信用户信息
     */
    WeiXinUserInfoVo getUserInfoByOpenId(String openId, String accessToken) ;

    /**
     * @return
     * @desc   获取微信用户信息
     */
    WeiXinUserInfoVo getUserInfoByOpen(Open open) ;
}
