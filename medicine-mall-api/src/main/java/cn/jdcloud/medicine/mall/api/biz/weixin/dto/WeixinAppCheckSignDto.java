package cn.jdcloud.medicine.mall.api.biz.weixin.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class WeixinAppCheckSignDto {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

    public  void sort(){
        List<String> checkSignParams = Lists.newArrayList(timestamp,nonce,echostr);
        Collections.sort(checkSignParams, String::compareTo);
    }
}
