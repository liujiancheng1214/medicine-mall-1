package cn.jdcloud.medicine.mall.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenQF
 * @desc 用户相关页面跳转
 * @date 2020/8/18 0018 10:10
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 用户列表展示页
     * @return
     */
    @GetMapping({"/list"})
    public String userList(HttpServletRequest request){
        return "main/user/user";
    }

    /**
     * 用户等级展示页
     * @return
     */
    @GetMapping({"/level/list"})
    public String userLevel(HttpServletRequest request){
        return "main/user/level";
    }

    /**
     * 优惠券类别展示页
     * @return
     */
    @GetMapping({"/coupon"})
    public String couponList(HttpServletRequest request){
        return "main/user/coupon";
    }

    /**
     * 优惠券领取记录展示页
     * @return
     */
    @GetMapping({"/couponRecord"})
    public String couponRecordList(HttpServletRequest request){
        return "main/user/couponRecord";
    }


}
