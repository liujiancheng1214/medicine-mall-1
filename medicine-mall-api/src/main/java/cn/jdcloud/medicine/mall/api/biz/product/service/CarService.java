package cn.jdcloud.medicine.mall.api.biz.product.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.product.vo.CarAddVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarUpdateVo;
import cn.jdcloud.medicine.mall.domain.product.Car;

public interface CarService  extends IService<Car>{

	CarItemVo  addCar(Integer userId,CarAddVo  carAddVo);
	
	 Car queryCarByUserIdAndItemNo(Integer userId,String itemNO,String sku);
	
	List<CarItemVo> listCarListByUserId(Integer userId,int pageNum,int pageSize);
	
	int deleteCar(Integer userId,Integer carId);
	
	CarItemVo updateCarNum(Integer  userId,CarUpdateVo carUpdateVo);
	
}
