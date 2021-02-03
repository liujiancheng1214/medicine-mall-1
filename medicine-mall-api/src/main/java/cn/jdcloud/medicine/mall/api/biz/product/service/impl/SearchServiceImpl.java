package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.config.ConfigService;
import cn.jdcloud.medicine.mall.api.biz.product.service.SearchService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.SearchItem;
import cn.jdcloud.medicine.mall.api.constant.Constant;
import cn.jdcloud.medicine.mall.dao.product.SearchMapper;
import cn.jdcloud.medicine.mall.domain.config.Config;
import cn.jdcloud.medicine.mall.domain.product.Search;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ConfigService configService;
	@Autowired
	private  SearchMapper  searchMapper;
	
	@Override
	public void addSearch(Integer userId, String searchValue) {
		// 如果关键字已经存在 则只更新时间字段
		Search search=querySearchBySearchValue(userId, searchValue);
		if(search==null) {
			search=new Search();
			search.setId(0);
			search.setCreateTime(new Date());
			search.setSearchValue(searchValue);
			search.setUserId(userId);
			searchMapper.insert(search);
		}
		else {
			search.setCreateTime(new Date());
			searchMapper.updateById(search);
		}
	}

	public Search querySearchBySearchValue(Integer userId,String searchValue) {
		if (StringUtils.isEmpty(searchValue)) {
			return null;
		}
		List<Search> list = searchMapper.selectList(new QueryWrapper<Search>()
				.eq("search_value", searchValue).eq("user_id", userId));
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * 查询个人搜索记录
	 */
	@Override
	public List<Search> listSearchByUserId(Integer userId) {
		/* 如果需要数量限制 则采用分页查询
		Page<Search> page=new Page<Search>();
		page.setCurrent(1);
		page.setSize(5);
		page=searchMapper.selectPage(page, new QueryWrapper<Search>()
				.eq("user_id", userId).orderByDesc("create_time"));
	    return page.getRecords();
		*/
		return searchMapper.selectList(new QueryWrapper<Search>()
				.eq("user_id", userId).orderByDesc("create_time"));
	}

	@Override
	public List<SearchItem> listhotItem() {
		Config config=configService.queryByCode(Constant.HOT_ITEM);
		if(config==null) {
			return new ArrayList<SearchItem>();
		}
	    List<SearchItem> list=new ArrayList<>();
		String[] str=config.getValue().split(Constant.STR_DIVISION);
		for(String s:str) {
			SearchItem  searchItem=new SearchItem();
			String[] ss=s.split(Constant.STR_DIVISION_);
			searchItem.setItemName(ss[1]);
			searchItem.setItemNo(ss[0]);
			list.add(searchItem);
		}
		return list;
	}

	@Override
	public List<SearchItem> listcommonlyUsedItem() {
		Config config=configService.queryByCode(Constant.COMMONLY_USED_ITEM);
		if(config==null) {
			return new ArrayList<SearchItem>();
		}
	    List<SearchItem> list=new ArrayList<>();
		String[] str=config.getValue().split(Constant.STR_DIVISION);
		for(String s:str) {
			SearchItem  searchItem=new SearchItem();
			String[] ss=s.split(Constant.STR_DIVISION_);
			searchItem.setItemName(ss[1]);
			searchItem.setItemNo(ss[0]);
			list.add(searchItem);
		}
		return list;
	}

	@Override
	public void deleteSearchByUserId(Integer userId) {
		searchMapper.delete(new QueryWrapper<Search>().eq("user_id", userId));
	}

}
