package cn.jdcloud.medicine.mall.api.biz.weixin.vo;

import lombok.Data;

/**
 * @author qun.xu
 * @desc   用户信息
 */
@Data
public class WeiXinUserInfoVo {

    private String openId;

    private String unionId;

    private String nickName;

    private String sex;

    private String headImgUrl;

}
