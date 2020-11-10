package cn.jdcloud.medicine.mall.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
* @ClassName:      RoleController
* @Description:    角色页面控制器
* @Author:         Fengbin
* @CreateDate:     2019/3/27 0027 上午 11:34
* @UpdateUser:     Fengbin
* @UpdateDate:     2019/3/27 0027 上午 11:34
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Controller
@RequestMapping("/sys/role/")
public class RoleController {

    @RequestMapping("list")
    public String list(){
        return "base/role/list";
    }

    @RequestMapping("rolePermTree")
    public String rolePermTree(HttpServletRequest request, @RequestParam("roleId") Integer roleId,@RequestParam("roleName") String roleName){
        request.setAttribute("roleId", roleId);
        request.setAttribute("roleName",roleName);
        return "base/role/rolepermtree";
    }
}
