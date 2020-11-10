package cn.jdcloud.medicine.mall.api.biz.product.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.product.vo.ProductListVO;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.Product;

public interface ProductService extends IService<Product>{
   
	 Page<ProductListVO> page(Page<Item> page, QueryWrapper<Item> queryWrapper);
}
