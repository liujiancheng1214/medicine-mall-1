package cn.jdcloud.medicine.mall.api.biz.weixin.rest;

import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserVo;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.CustomerRegDto;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.Open;
import cn.jdcloud.medicine.mall.api.biz.weixin.dto.RequestUrlSign;
import cn.jdcloud.medicine.mall.api.biz.weixin.service.WeixinOfficialService;
import cn.jdcloud.medicine.mall.api.biz.weixin.vo.CodeLoginVo;
import cn.jdcloud.medicine.mall.api.biz.weixin.vo.UserLoginVo;
import cn.jdcloud.medicine.mall.api.biz.weixin.vo.WeiXinUserInfoVo;
import cn.jdcloud.medicine.mall.api.sms.service.SmsCodeService;
import cn.jdcloud.medicine.mall.api.token.service.TokenService;
import cn.jdcloud.medicine.mall.domain.token.Token;
import cn.jdcloud.medicine.mall.domain.user.User;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @desc   微信公众号相关接口
 * @date   2018.03.15
 */
@RestController
@Slf4j
@RequestMapping(value = "/weixin")
public class WeixinUserRest extends BaseController {

    @Resource Gson gson;

    /**
     * 微信公众号服务类
     */
    @Resource
    WeixinOfficialService weixinOfficialService;

    @Resource
    UserService userService;

    @Resource
    TokenService tokenService;

    Logger logger  = LoggerFactory.getLogger(this.getClass());

    @Resource
    SmsCodeService smsCodeService;






    /**
     * @desc  获取分享路径签名，其余部分全部由js-api调用微信接口完成
     */
    @RequestMapping(value = "/official/share/sign/get", method = RequestMethod.GET)
    @LoginRequired(value = false)
    public ApiResult getRequestUrlSign(@RequestParam("url")String url,
                                       @RequestParam(name = "fromAccount",defaultValue = "yaxx1688") String fromAccount) throws  Exception{
        RequestUrlSign requestUrlSign = weixinOfficialService.getShareUrlSign(url,fromAccount);
        Gson gson  = new Gson();
        logger.info("requestSign:"+gson.toJson(requestUrlSign).toString());
        return  ApiResult.ok(requestUrlSign);
    }

    /**
     * 根据code获取openId
     * @return   返回openId
     */
    @RequestMapping(value = "/official/openId/get/{code}", method = RequestMethod.GET)
    @LoginRequired(false)
    public ApiResult getOpenId(@PathVariable(value = "code") String code,
                              @RequestParam(name = "fromAccount",defaultValue = "yaxx1688") String fromAccount) {
         Open open =   weixinOfficialService.getOpenObject(code);
         log.info("###########request  method getOpenId,result:{}",gson.toJson(open));
        return  ApiResult.ok(open);
    }

    @RequestMapping(value = "/official/user/get/{code}", method = RequestMethod.GET)
    @LoginRequired(false)
    public ApiResult getUserInfo(@PathVariable(value = "code") String code, @RequestParam(name = "fromAccount",defaultValue = "yaxx1688") String fromAccount) {
        Open open =  weixinOfficialService.getOpenObject(code);
        WeiXinUserInfoVo weiXinUserInfoVo =  weixinOfficialService.getUserInfoByOpen(open);
        log.info("###########request  method getUserInfo,result:{}",gson.toJson(weiXinUserInfoVo));
        return  ApiResult.ok(weiXinUserInfoVo);
    }


    @GetMapping("/official/token")
    @LoginRequired(false)
    public ApiResult getAccessToken(@RequestParam(name = "fromAccount",defaultValue = "yaxx1688") String fromAccount) {
        log.info("#########get official token ,from account :{}##############",fromAccount);
        String accessToken = weixinOfficialService.getAccessToken(fromAccount);
        log.info("#########official access token :{}########## ",accessToken);
        return  ApiResult.ok(accessToken);
    }

}
