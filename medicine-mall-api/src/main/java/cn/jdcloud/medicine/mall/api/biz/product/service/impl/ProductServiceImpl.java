package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import cn.jdcloud.medicine.mall.api.biz.product.service.ProductService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ProductListVO;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.product.ProductMapper;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.Product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 商品相关业务层
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ItemMapper itemMapper;
	@Override
	public Page<ProductListVO> page(Page<Item> page, QueryWrapper<Item> queryWrapper) {
		  Page<Item> itemPage = itemMapper.selectPage(page, queryWrapper);
		  
		  
		  return null;
	}

}
