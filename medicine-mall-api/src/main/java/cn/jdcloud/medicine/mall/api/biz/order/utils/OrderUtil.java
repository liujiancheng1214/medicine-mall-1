package cn.jdcloud.medicine.mall.api.biz.order.utils;

import cn.jdcloud.framework.utils.DateUtils;

import java.util.Random;

public class OrderUtil {


    public static String randomOrderId(){
        StringBuilder sb = new StringBuilder();
        String init = DateUtils.currentTime();
        sb.append(init);

        Random random = new Random();
        int r = random.nextInt(1000);
        String suff = String.valueOf(r);
        switch (suff.length()){
            case 1:
                sb.append("00");
                break;
            case 2:
                sb.append("0");
                break;
            case 3:
                break;
            default:break;
        }
        sb.append(r);
        return sb.toString();
    }
}
