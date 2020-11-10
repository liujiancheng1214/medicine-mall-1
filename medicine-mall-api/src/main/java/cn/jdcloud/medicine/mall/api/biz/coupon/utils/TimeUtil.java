package cn.jdcloud.medicine.mall.api.biz.coupon.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author chenQF
 * @desc 时间相关工具类
 * @date 2020/8/21 0021 10:56
 */
public class TimeUtil {

    /**
     * @desc 给Date类型数据加天数
     * @param date
     * @param dayCount
     * @return
     */
    public static Date dateAddDays(Date date, Integer dayCount){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,dayCount); //把日期往后增加dayCount天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推dayCount天的结果
    }
}
