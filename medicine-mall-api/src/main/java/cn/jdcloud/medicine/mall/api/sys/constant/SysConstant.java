package cn.jdcloud.medicine.mall.api.sys.constant;

import cn.jdcloud.framework.core.common.ConstantLoader;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by yanghuoyun on 2017/3/31.
 */
public class SysConstant implements ConstantLoader{

    /** =====================常量final区======================**/
    public static final String MODULE = "system";
    public static final int FROM_OWNER=1;
    public static final int FROM_DRIVER=2;



    /** =====================常量配置区======================**/
    public static boolean IS_TEST = true;
    //token unit hour
//    public static int TOKEN_ACC_EXPIRES;
//    public static int TOKEN_REF_EXPIRES;
    //
    public static String EXPS100_KEY = "TDIQXCtS2210";
    public static String EXPS100_CUSTOMER = "1D2988191A3BCF50B9E7B8B464AE7282";
    public static String EXPS100_QUERY_URL = "https://poll.kuaidi100.com/poll/query.do";

    public static Integer DEFAULT_SELLERID = 1;

    public static BigDecimal MIN_AMOUNT = new BigDecimal(100);

    @Override
    public void load(Map<String,String> map) {
        IS_TEST =  Boolean.parseBoolean(map.get("system.isTest"));
        EXPS100_KEY = map.get("express100.key");
        EXPS100_CUSTOMER = map.get("express100.customer");
        EXPS100_QUERY_URL = map.get("express100.query.url");
//        TOKEN_ACC_EXPIRES = Integer.parseInt(map.get("system.token.acc.expires"));
//        TOKEN_REF_EXPIRES = Integer.parseInt(map.get("system.token.ref.expires"));
//        MIN_AMOUNT = new BigDecimal(map.get("system.balance.minAmount"));
    }
}
