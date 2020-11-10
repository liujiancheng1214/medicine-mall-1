package cn.jdcloud.medicine.mall.api.biz.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.user.vo.FootprintVo;
import cn.jdcloud.medicine.mall.domain.user.Footprint;

public interface FootprintService extends IService<Footprint> {
	
	Footprint addFootprint(Integer userId,String itemNo);

	List<FootprintVo> listFootprint(Integer userId,int pageNum,int pageSize);
	
	int deleteFootprint(Integer userId,List<Integer>ids);
}
