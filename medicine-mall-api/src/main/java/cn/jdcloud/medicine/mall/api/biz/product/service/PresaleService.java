package cn.jdcloud.medicine.mall.api.biz.product.service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.medicine.mall.api.biz.product.vo.PresaleItemVo;

public interface PresaleService {

	int  addPresale(int  itemId,Date sendDate);
	
	Page<PresaleItemVo> pagePresale(int pageNum,int pageSize,String searchValue);
}
