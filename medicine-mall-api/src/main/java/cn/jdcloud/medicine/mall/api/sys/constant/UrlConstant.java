package cn.jdcloud.medicine.mall.api.sys.constant;

import cn.jdcloud.framework.core.common.ConstantLoader;

import java.util.Map;

/**
 * @author qun.xu
 * @desc   url constant
 */
public class UrlConstant implements ConstantLoader {

    public static  String DISTRIBUTE_URL = "";

    public static  String COURSE_DETAIL_URL = "";

    @Override
    public void load(Map<String,String> map) {
        DISTRIBUTE_URL = map.get("url.distribute.prefix");

        COURSE_DETAIL_URL = map.get("url.course.preview");
    }
}
