package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.medicine.mall.api.biz.product.service.PresaleService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PresaleItemVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.product.ItemBrandMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemCategoryMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.product.PresaleMapper;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import cn.jdcloud.medicine.mall.domain.product.Presale;


@Service
public class PresaleServiceImpl implements PresaleService {

	@Autowired
	private PresaleMapper presaleMapper;
	@Autowired
	private ItemMapper itemMapper;
	
	 @Resource
	 ItemBrandMapper itemBrandMapper; 
	 
	 @Resource
	 ItemCategoryMapper itemCategoryMapper;
	
	@Override
	public int addPresale(int itemId, Date sendDate) {
		Presale presale=new Presale();
		presale.setId(0);
		Item  item = itemMapper.selectById(itemId);
		// 查询品牌名称
		ItemBrand  itemBrand=itemBrandMapper.selectById(item.getItemBrandId());
		//查询类别名称
		ItemCategory  itemCategory=itemCategoryMapper.selectById(item.getItemCategoryId());
		presale.setBrandName(itemBrand.getIbrandName());
		presale.setCatogeryName(itemCategory.getCategoryName());
		presale.setItemId(itemId);
		presale.setSendDate(sendDate);
		return presaleMapper.insert(presale);
	}

	@Override
	public Page<PresaleItemVo> pagePresale(int pageNum, int pageSize, String searchValue) {
		Page<Presale> page=new Page<Presale>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		
		QueryWrapper<Presale> queryWrapper =new QueryWrapper<Presale>();
		// 发货时间大于当前时间
		queryWrapper.gt("send_date", new Date());
		queryWrapper.like(searchValue!=null  ,"brand_name", searchValue)
		.or().like(searchValue!=null  ,"catogery_name", searchValue);
		page=presaleMapper.selectPage(page, queryWrapper);
		
		Page<PresaleItemVo> pageVo=new Page<PresaleItemVo>();
		BeanUtil.copyProperties(page, pageVo);
		List<PresaleItemVo> list=new ArrayList<>();
		for(Presale  p:page.getRecords()) {
			  PresaleItemVo vo=new PresaleItemVo();
			  Integer itemId = p.getItemId();
			  Item  item = itemMapper.selectById(itemId);
			  BeanUtil.copyProperties(item, vo);
			  vo.setBrandName(p.getBrandName());
			  vo.setCatogeryName(p.getCatogeryName());
			  vo.setSendDate(p.getSendDate());
			  vo.setItemId(itemId);
			  vo.setLimitNum(p.getLimitNum());
			  list.add(vo);
		}
		pageVo.setRecords(list);
		return pageVo;
	}

}
