package cn.jdcloud.medicine.mall.api.biz.integral.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.medicine.mall.api.biz.integral.service.vo.IntegralVo;

public interface IntegralService {
   
	Page<IntegralVo> pageIntegral(int pageNum,int pageSize,Integer userId,Integer changeType);
	
	void obtainIntegral(Integer userId,String type,Integer itemId);
	
	void  useIntegral(Integer userId,String type,Integer itemId,Integer couponId);
	
}
