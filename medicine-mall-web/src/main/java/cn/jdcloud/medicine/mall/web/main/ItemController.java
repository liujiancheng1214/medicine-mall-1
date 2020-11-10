package cn.jdcloud.medicine.mall.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenQF
 * @desc 商品设置页面跳转
 * @date 2020/8/18 0018 10:10
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    /**
     * 商品列表页
     * @return
     */
    @GetMapping({"/list"})
    public String itemList(HttpServletRequest request){
        return "main/item/list";
    }
    /**
     * 商品品牌页
     * @return
     */
    @GetMapping({"/brand"})
    public String itemBrand(HttpServletRequest request){
        return "main/item/brand";
    }
    /**
     * 商品类目页
     * @return
     */
    @GetMapping({"/category"})
    public String itemCategory(HttpServletRequest request){
        return "main/item/category";
    }

}
