package cn.jdcloud.medicine.mall.api.biz.product.rest;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.medicine.mall.api.biz.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺入口
 */

@RestController
@RequestMapping("/app/seller/product")
public class SellerProductRest extends BaseController {

    @Autowired
    ProductService productService;

}

