package cn.jdcloud.medicine.mall.api.biz.product.service;

import java.util.List;

import cn.jdcloud.medicine.mall.api.biz.product.vo.SearchItem;
import cn.jdcloud.medicine.mall.domain.product.Search;

public interface SearchService {

	void deleteSearchByUserId(Integer userId);
	
	void addSearch(Integer userId,String searchValue);
	
	List<Search> listSearchByUserId(Integer userId);
	
	List<SearchItem> listhotItem();
	
	List<SearchItem> listcommonlyUsedItem();
}
