package cn.jdcloud.medicine.mall.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sys/admin/userTree")
public class UserTreeController {

    /**
     * 用户树
     * @return
     */
    @GetMapping("userList")
    public String userTree(HttpServletRequest request){
        return "admin/userTree/userList";
    }

}













