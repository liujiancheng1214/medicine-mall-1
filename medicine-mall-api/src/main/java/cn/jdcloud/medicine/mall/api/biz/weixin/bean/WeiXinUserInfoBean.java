package cn.jdcloud.medicine.mall.api.biz.weixin.bean;

import lombok.Data;

/**
 * @desc 微信用户信息Bean
 */
@Data
public class WeiXinUserInfoBean {


    //
    //    {"openid":"oVLwC0vSdC0amTL-h3KrMPatW2N0","nickname":"C.C","sex":1,"language":"zh_CN","city":"南昌","province":"江西","country":"中国",
    //            "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTLq3wPGic2ibDTibr5ic5LJXZbAlR7pUFkck0QiadyO5fk1FDNyRVXNpbuqVkvzj5gJlaAIJYicxCfbb04A\/132",
    //            "privilege":[],"unionid":"oJ0BwwEqLhvIWcmiGJfzHQQ9MoUw"}

    private String openid;

    private String nickname;

    private String sex;

    private String language;

    private String city;

    private String province;

    private String  country;

    private String headimgurl;

    private String []privilege;

    private String unionid;



}
