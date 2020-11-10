package cn.jdcloud.medicine.mall.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenQF
 * @desc 系统设置页面跳转
 * @date 2020/8/18 0018 10:10
 */
@Controller
@RequestMapping("/sys")
public class SysController {

    @GetMapping({"/admin"})
    public String mainSysAdmin(HttpServletRequest request){
        return "main/sys/admin";
    }

    /**
     * 角色设置页
     * @return
     */
    @GetMapping({"/role"})
    public String role(HttpServletRequest request){
        return "main/sys/role";
    }

    /**
     * 权限设置页
     * @return
     */
    @GetMapping({"/permission"})
    public String permission(HttpServletRequest request){
        return "main/sys/permission";
    }

}
