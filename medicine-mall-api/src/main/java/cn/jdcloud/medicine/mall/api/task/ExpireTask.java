package cn.jdcloud.medicine.mall.api.task;

import cn.jdcloud.framework.common.Cron;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单超时
 */

@Component
public class ExpireTask {

    @Scheduled(cron = Cron.PER_DAY_0005)
    public void expireOrder(){
        //24小时订单失效
    }

    @Scheduled(cron = Cron.PER_DAY_0005)
    @Transactional
    public void expireCourse(){
//       // 删除 课程过期删除订单,删除课程-用户关联
//       List<UserCourse> userCourseList = userCourseMapper.selectUserExpireCourse();
//       if(userCourseList.size()!=0){
//           orderMapper.deleteExpireCourseOrder(userCourseList);
//           userCourseMapper.deleteExpireUserCourse(userCourseList);
//       }
//       throw  new RuntimeException("123123");
    }
}
