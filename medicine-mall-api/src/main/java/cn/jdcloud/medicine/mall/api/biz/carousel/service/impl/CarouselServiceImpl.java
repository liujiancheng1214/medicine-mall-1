package cn.jdcloud.medicine.mall.api.biz.carousel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.jdcloud.medicine.mall.api.biz.carousel.service.CarouselService;
import cn.jdcloud.medicine.mall.api.biz.carousel.vo.CarouselVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.carousel.CarouselMapper;
import cn.jdcloud.medicine.mall.domain.carousel.Carousel;


@Service
public class CarouselServiceImpl implements CarouselService {

	@Autowired
	private CarouselMapper carouselMapper;
	
	@Override
	public List<CarouselVo> listCarousel() {
		 QueryWrapper<Carousel> queryWrapper=new QueryWrapper();
		 queryWrapper.eq("state", 1).orderByAsc("sort");
		 List<Carousel> list=carouselMapper.selectList(queryWrapper);
		 List<CarouselVo> voList= BeanUtil.copyPropsForList(list, CarouselVo.class);
		 return voList;
	}
}
