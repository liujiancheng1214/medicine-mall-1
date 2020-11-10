package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.product.service.CarService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarAddVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CarUpdateVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.product.CarMapper;
import cn.jdcloud.medicine.mall.domain.product.Car;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;


@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

	@Autowired
	private CarMapper carMapper;
	@Autowired
	private ItemService  itemService;
	@Autowired
	private ItemBatchService itemBatchService;
	
	
	@Override
	public CarItemVo addCar(Integer userId,CarAddVo carAddVo) {
		// 如果商品已经存在 则只更新数量
		Car car=queryCarByUserIdAndItemNo(userId,carAddVo.getItemNo(),carAddVo.getSku());
		if(car==null) {
			car=new Car();
			car.setId(0);
			ItemBatch itemBatch=	itemBatchService.queryItemBatchBySkuAndItemNo(carAddVo.getSku(), carAddVo.getItemNo());
			car.setBatchNo(itemBatch.getBatchNo());
			car.setSku(carAddVo.getSku());
			car.setCreateTime(new Date());
			car.setItemNo(carAddVo.getItemNo());
			car.setNum(carAddVo.getNum());
			car.setStatus(0);
			car.setUpdateTime(new Date());
			car.setUserId(userId);
			this.save(car);
		}
		else {
			car.setUpdateTime(new Date());
			car.setNum(car.getNum()+carAddVo.getNum());
			this.updateById(car);
		}
		return changeCar(car);
	}

	@Override
	public List<CarItemVo> listCarListByUserId(Integer userId, int pageNum, int pageSize) {
		Page<Car> page=new Page<Car>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		QueryWrapper<Car> queryWrapper=new QueryWrapper<Car>();
		queryWrapper.eq("user_id", userId).eq("is_delete", 0).eq("status", 0);
	    Page<Car> itemPage = carMapper.selectPage(page, queryWrapper);
	    List<Car> cars=itemPage.getRecords();
	    List<CarItemVo> list=new ArrayList<>();
		for(Car c:cars) {
			list.add(changeCar(c));
		}
		return list;
	}

	@Override
	public Car queryCarByUserIdAndItemNo(Integer userId,String itemNO,String sku) {
		QueryWrapper<Car> queryWrapper=new QueryWrapper<Car>();
		queryWrapper.eq("user_id", userId).eq("item_no", itemNO).eq("sku", sku)
		.eq("is_delete", 0).eq("status", 0);
		List<Car> list=carMapper.selectList(queryWrapper);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	private CarItemVo changeCar(Car car) {
		String itemNo=car.getItemNo();
		Item item=itemService.queryItemByItemNo(itemNo);
		CarItemVo vo=new CarItemVo();
		vo.setCar(car);
		ItemVo itemVo=new ItemVo();
		BeanUtil.copyProperties(item, itemVo);
		vo.setItemVo(itemVo);
		return vo;
	}
	
	@Override
	public int deleteCar(Integer userId,Integer carId) {
		Car car=carMapper.selectById(carId);
		if(car!=null) {
			if(userId==car.getUserId()) {
				car.setIsDelete(1);
				car.setUpdateTime(new Date());
				return carMapper.updateById(car);
			}
		}
		return 0;
	}

	@Override
	public CarItemVo updateCarNum(Integer userId, CarUpdateVo carUpdateVo) {
		Car car=carMapper.selectById(carUpdateVo.getCarId());
		if(car!=null) {
			if(userId==car.getUserId()) {
				car.setNum(carUpdateVo.getNum());
				car.setUpdateTime(new Date());
			    carMapper.updateById(car);
			    car= carMapper.selectById(carUpdateVo.getCarId());
			    return changeCar(car);
			}
		}
		return null;
	}
}
