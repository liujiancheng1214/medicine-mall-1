package cn.jdcloud.medicine.mall.web.main;

import cn.jdcloud.medicine.mall.client.user.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${web.base.url}")
    private String baseUrl;

    /**
     * 登录页
     * @return
     */
    @GetMapping({"/","","login"})
    public String login(HttpServletRequest request){
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        if(session!=null){
            return "redirect:"+baseUrl+"/main";
        }
        return "main/login";
    }

    @GetMapping({"/main"})
    public String main(HttpServletRequest request){
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        if(session==null){
            return "redirect:"+baseUrl+"/login";
        }
        return "main/index";
    }

    /**
     * 首页
     * @return
     */
    @GetMapping({"/home"})
    public String index(HttpServletRequest request){
        return "main/home";
    }

}













