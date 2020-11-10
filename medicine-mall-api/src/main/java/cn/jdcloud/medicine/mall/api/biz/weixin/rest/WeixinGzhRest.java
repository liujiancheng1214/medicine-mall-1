package cn.jdcloud.medicine.mall.api.biz.weixin.rest;

import cn.jdcloud.medicine.mall.api.biz.weixin.bo.UrlSign;
import cn.jdcloud.medicine.mall.api.biz.weixin.service.GzhService;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @desc   微信公众号相关接口
 * @date   2018.03.15
 */
@RestController
@Slf4j
@RequestMapping(value = "/weixin/gzh")
public class WeixinGzhRest extends BaseController {

    Logger logger  = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信公众号服务类
     */
    @Resource
    GzhService gzhService;


    /**
     * @param url
     * @return  ResponseVo
     * @throws Exception
     * @desc  获取分享路径签名，其余部分全部由js-api调用微信接口完成
     */
    @GetMapping("/urlSign")
    public ApiResult getUrlSign(@RequestParam("appId") String appId,
                                         @RequestParam("url")String url) throws  Exception{
        UrlSign requestUrlSign = gzhService.getUrlSign(appId,url);
        return  ApiResult.ok(requestUrlSign);
    }

}
