package cn.jdcloud.medicine.mall.api.biz.share.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.medicine.mall.api.biz.share.vo.ShareGroupUser;

public interface ShareGroupService {

	// 增加分享领取记录
	void addShareGroup(Integer userId,Integer shareId);
	
	//查询分享领取记录
	Page<ShareGroupUser> pageShareGroupUser(int pageNum,int pageSize);
}
