package cn.jdcloud.medicine.mall.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenQF
 * @desc 促销活动设置页面跳转
 * @date 2020/8/18 0018 10:10
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController {

    /**
     * 促销活动页
     * @return
     */
    @GetMapping({"/list"})
    public String promotion(HttpServletRequest request){
        return "promotion/promotion";
    }

    /**
     * 团购模板记录列表展示页
     * @return
     */
    @GetMapping({"/group"})
    public String promotionGroupList(HttpServletRequest request){
        return "promotion/promotionGroup";
    }

    /**
     * 团购模板记录列表展示页
     * @return
     */
    @GetMapping({"/groupInfo"})
    public String groupInfoList(HttpServletRequest request){
        return "promotion/groupInfo";
    }
}
