package cn.jdcloud.medicine.mall.api;

import cn.jdcloud.medicine.mall.api.biz.pay.constant.AlipayConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.sms.constant.SmsConstant;
import cn.jdcloud.medicine.mall.api.sys.constant.AppConstant;
import cn.jdcloud.medicine.mall.api.sys.constant.SysConstant;
import cn.jdcloud.medicine.mall.api.sys.constant.UrlConstant;
import cn.jdcloud.medicine.mall.api.sys.service.SysConsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Order(2)
public class ConstantsLoader implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(ConstantsLoader.class);

    @Resource
    private SysConsService sysConsService;

    public static String filePath;

    @Override
    public void run(String... args) throws Exception {
        //加载系统配置项
        Map<String,String> map = sysConsService.getKeyValueMap();
        SysConstant sysConstant = new SysConstant();
        sysConstant.load(map);

        //加载支付宝配置
        AlipayConstant alipayConstant = new AlipayConstant();
        alipayConstant.load(map);

        //加载微信支付配置
        WeixinConstant weixinConstant = new WeixinConstant();
        weixinConstant.load(map);

        //加载app配置
        AppConstant appConstant = new AppConstant();
        appConstant.load(map);

        //加载sms短信配置
        SmsConstant smsConstant = new SmsConstant();
        smsConstant.load(map);

        UrlConstant urlConstant = new UrlConstant();
        urlConstant.load(map);

    }
}
