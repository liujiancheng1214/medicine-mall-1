package cn.jdcloud.medicine.mall.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单管理页面跳转
 *
 * @author HuZhengYu
 * @date 18:19 2020/8/20
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    /**
     * 订单列表
     *
     * @param request
     * @return java.lang.String
     * @author HuZhengYu
     * @date 18:19 2020/8/20
     */
    @GetMapping({"/list"})
    public String userList(HttpServletRequest request){
        return "main/order/list";
    }


}
