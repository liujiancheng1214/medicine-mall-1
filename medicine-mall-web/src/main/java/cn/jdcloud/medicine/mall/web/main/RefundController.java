package cn.jdcloud.medicine.mall.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName RefundController
 * @Author wuzhiyong
 * @Date 2020/8/25 13:54
 * @Version 1.0
 **/
@Controller
@RequestMapping("/refund")
public class RefundController {

    /**
     * 退款管理页
     * @return
     */
    @GetMapping({"/list"})
    public String refundList(HttpServletRequest request){
        return "main/refund/list";
    }
    /**
     * 退款原因管理页
     * @return
     */
    @GetMapping({"/reason/list"})
    public String refundeasonList(HttpServletRequest request){
        return "main/refund/reasonList";
    }
}
