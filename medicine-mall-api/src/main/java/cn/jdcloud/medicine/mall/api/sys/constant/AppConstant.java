package cn.jdcloud.medicine.mall.api.sys.constant;

import cn.jdcloud.framework.core.common.ConstantLoader;

import java.util.Map;

/**
 * Created by yanghuoyun on 2017/4/11.
 */
public class
AppConstant implements ConstantLoader {

    /** ============================final定义区=====================================**/
    public static final String MODULE = "app";
    public static final String APP_NAME = "医药商城";
    public static final byte WEB = 1;
//    public static final byte H5 = 1;
    /** =============================配置区=======================================**/


    /**
     * 客服部负责人手机号码  如果订单表的车主id(carrier_id)为客服部负责人(手机号13870838813)，则不发送短信给司机

     */
    public static String CUSTOMER_SERVER_DEPARTMENT_PRINCIPAL_MOBILE = "13870838813";


    public static String TEST_DEFAULT_MOBILE = "13900009999";
    public static String TEST_DEFAULT_VALID_CODE = "9999";


    /**
     * 加载数据库配置的常量
     * @param map
     */
    @Override
    public void load(Map<String,String> map) {

    }
}
