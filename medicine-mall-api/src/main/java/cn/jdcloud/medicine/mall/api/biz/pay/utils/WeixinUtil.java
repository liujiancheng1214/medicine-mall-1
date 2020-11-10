package cn.jdcloud.medicine.mall.api.biz.pay.utils;

import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.framework.utils.XmlUtil;
import cn.jdcloud.framework.utils.security.MD5Util;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.support.NameValue;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yanghuoyun on 2017/6/7.
 */
public class WeixinUtil {

    public static final BigDecimal HUNDRUD = new BigDecimal(100);

    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static final long EXPIRED = 30 * 60 * 1000;

    public static final String SIGN_MD5 = "MD5";
    public static final String SIGN_SHA1 = "SHA1";


    public static Integer covertAmount(BigDecimal payAmount){
        if(payAmount==null){
            return 0;
        }
        return payAmount.multiply(HUNDRUD).intValue();
    }

    public static String formatDate(Date time){
        return DateUtils.formatDate(time,DATE_FORMAT);
    }

    public static String getExpiredTime(Date startTime){
        Date endTime = new Date(startTime.getTime()+EXPIRED);
        return formatDate(endTime);
    }

    public static String Md5Sign(Map<String,String> params,Byte clientType){
        if(params==null){
            return null;
        }
        String signStr = wrapSignStr(params, clientType);
        return MD5Util.encrypt(signStr).toUpperCase();
    }

    public static String wrapSignStr(Map<String,String> params, Byte clientType){
        List<NameValue> list = new ArrayList<NameValue>(params.size());
        params.forEach((k,v)->list.add(new NameValue(k,v)));
        Collections.sort(list,(o1,o2)->{
            return o1.getName().compareTo(o2.getName());
        });
        StringBuilder sb = new StringBuilder();
        list.forEach(nv->{
            if(nv.getName()!=null&&nv.getValue()!=null){
                sb.append(nv.getName()).append("=");
                sb.append(nv.getValue()).append("&");
            }
        });
        String key = WeixinConstant.PARTNER_KEY;
        sb.append("key=").append(key);
        return sb.toString();
    }

    public static String postRequest(String uri,Object obj){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        String xmlBody = XmlUtil.toXmlString(obj);
        CloseableHttpResponse response =null;
        try {
            StringEntity entity = new StringEntity(xmlBody,HttpUtil.UTF8);
            post.setEntity(entity);
            response = client.execute(post);
            return HttpUtil.getResponseBodyAsString(response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            post.releaseConnection();
            try {
                client.close();
                if(response!=null){
                    response.close();
                }
            }catch (IOException e){
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Long a =1L;
        Long b =null;
        System.out.println(a.equals(b));
    }

    public static String randomUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }


}
