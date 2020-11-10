package cn.jdcloud.medicine.mall.api.biz.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponRecordService;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.ItemNumVo;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderInfoService;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderPayPageService;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderItemListVo;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderPayPageVO;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PageItemVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionInfoVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionItemVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserAddressService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddressVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.coupon.OrderCouponMapper;
import cn.jdcloud.medicine.mall.dao.order.OrderMapper;
import cn.jdcloud.medicine.mall.dao.product.CarMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemBrandMapper;
import cn.jdcloud.medicine.mall.dao.promotion.GroupInfoMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.product.Car;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItem;

@Service
public class OrderPayPageServiceImpl implements OrderPayPageService {

	@Autowired
	private ItemService itemService;
	@Autowired
	private UserAddressService userAddressService;
	@Resource
	private ItemBatchService itemBatchService;
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private CouponRecordService couponRecordService;
	@Autowired
	private ItemBrandMapper itemBrandMapper;
	@Autowired
	private GroupInfoService groupInfoService;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private OrderCouponMapper orderCouponMapper;
	@Autowired
	private GroupInfoMapper groupInfoMapper;
	@Autowired
	private PromotionGroupItemService promotionGroupItemService;
	@Autowired
	private PromotionInfoMapper promotionInfoMapper;
	@Override
	public OrderPayPageVO payPageByItem(Integer userId, String itemNO,int promotionTag,Integer promotionId,Integer groupInfoId, String sku, int num) {
		OrderPayPageVO vo = new OrderPayPageVO();
		vo.setType("1");
		ItemVo itemVo = itemService.queryItemVoByItemNo(itemNO);
		List<PageItemVo>pageItemList=new ArrayList<>();
		PageItemVo pageItemVo=new PageItemVo();
		BeanUtil.copyProperties(itemVo, pageItemVo);
		pageItemVo.setNum(num);
		pageItemVo.setItemBrandName(brandName(pageItemVo.getItemBrandId()));
		pageItemList.add(pageItemVo);
		vo.setPageItemVoList(pageItemList);
		UserAddressVo userAddressVo = userAddressService.queryDefaultAddress(userId);
		if (userAddressVo != null) {
			vo.setDefaultAddressTag(1);
			vo.setAddressId(userAddressVo.getId());
			vo.setUserDefaultAddressVo(userAddressVo);
		}
		
		pageItemVo.setRetailPrice(itemVo.getRetailPrice());
		BigDecimal totalItemFee =null;
		if(promotionTag==0) {
			ItemBatch itemBatch = itemBatchService.queryItemBatchBySkuAndItemNo(sku, itemNO);
			 totalItemFee = itemBatch.getPrice().multiply(new BigDecimal(num));
			pageItemVo.setPlatformPrice(itemBatch.getPrice());
			pageItemVo.setSku(sku);
			pageItemVo.setItemBatchNo(itemBatch.getBatchNo());
			// 是否有可用优惠券标识
			List<ItemNumVo> list=new ArrayList<>();
			ItemNumVo itemNumVo=new ItemNumVo();
			itemNumVo.setItemId(itemVo.getId());
			itemNumVo.setItemNo(itemNO);
			itemNumVo.setNum(num);
			itemNumVo.setSku(sku);
			list.add(itemNumVo);
			vo.setCouponTag(couponTag(userId, list));
		}
		//  团购商品处理
		else {
			PromotionItem promotionItem=promotionGroupItemService.promotionItemOne(promotionId, itemNO);
			vo.setCouponTag(0);
			pageItemVo.setPlatformPrice(promotionItem.getPromotionPrice());
			pageItemVo.setNum(promotionItem.getItemNum()*num);
			totalItemFee=promotionItem.getPromotionPrice().multiply(new BigDecimal(pageItemVo.getNum()));
			// 价格采用拼团价格  自己发起拼团
		/*	if(groupInfoId==null) {
				PromotionInfo promotionInfo=promotionInfoMapper.selectById(promotionId);
				GroupInfo groupInfo=new GroupInfo();
				groupInfo.setId(0);
				groupInfo.setCreateTime(now);
				groupInfo.setEndTime(promotionInfo.getEndTime());
				// 该数量为表中配置的单份份额
				groupInfo.setItemNum(num);
				groupInfo.setPlushUserId(userId);
				groupInfo.setPromotionId(promotionId);
				// 拼团状态 待支付
				groupInfo.setStatus(0);
				groupInfo.setUserNum(1);
				groupInfo.setUpdateTime(now);
				groupInfoMapper.insert(groupInfo);
				PromotionItem promotionItem=promotionGroupItemService.promotionItemOne(promotionId, itemNO);
				pageItemVo.setPlatformPrice(promotionItem.getPromotionPrice());
				pageItemVo.setRetailPrice(itemVo.getRetailPrice());
				// 不能使用优惠券
				vo.setCouponTag(0);
			}
			else {
				// TODO 是否可以参加拼团校验  人数  拼团失效时间 状态
				PromotionInfoVo promotionInfoVo=groupInfoService.queryPromotionInfoByGroupInfoId(groupInfoId);
				List<PromotionItem> promotionItemList=promotionGroupItemService.PromotionGroupItemInfo(promotionInfoVo.getPromotionVo().getPromotionId());
			    for(PromotionItem promotionItem:promotionItemList) {
			    	if(promotionItem.getItemNo().equals(itemNO)) {
			    		promotionInfoVo.setPromotionItemVo(new PromotionItemVo(promotionItem));
			    		pageItemVo.setPlatformPrice(promotionItem.getPromotionPrice());
			    		pageItemVo.setRetailPrice(itemVo.getRetailPrice());
			    		break;
			    	}
			    }
			    PromotionVo promotionVo= promotionInfoVo.getPromotionVo();
			    // 不能使用优惠券
			   
			}*/
		}
		 // 运费
		 vo.setTransportFee(new BigDecimal(10));
		 vo.setTotalItemFee(totalItemFee);
		 BigDecimal totalFee = totalItemFee.add(vo.getTransportFee());
		 vo.setTotalFee(totalFee);
		 return vo;
	}

	private int couponTag(Integer userId, List<ItemNumVo> list) {
		 List<CouponVo> couponVoList=couponRecordService.listCouponRecordByUserIdAndItemNos(userId, list);
		 if(couponVoList!=null&&couponVoList.size()>0) {
			 return 1;
		 }
		 return 0;
	}
	
	
	private String brandName(Integer brandId) {
		ItemBrand itemBrand=itemBrandMapper.selectById(brandId);
		if(itemBrand!=null) {
			return itemBrand.getIbrandName();
		}
		return "";
	}
	
	
	@Override
	public OrderPayPageVO payPageByCar(Integer userId, List<String> carIds) {
		OrderPayPageVO  orderPayPageVO=new OrderPayPageVO();
	    List<Car>cars=	carMapper.selectList(new QueryWrapper<Car>().in("id", carIds).eq("user_id", userId));
	    List<PageItemVo> pageItemList=new ArrayList<>();
	    BigDecimal totalItemFee = new BigDecimal(0);
	    
	    List<ItemNumVo> list=new ArrayList<>();
	    
	    for(Car car:cars) {
			Item itemVo = itemService.queryItemByItemNo(car.getItemNo());
			PageItemVo  pageItemVo=new PageItemVo();
			BeanUtil.copyProperties(itemVo, pageItemVo);
			pageItemVo.setNum(car.getNum());
			pageItemVo.setFactory(itemVo.getFactory());
			// pageItemVo.setItemBrandName(brandName(pageItemVo.getItemBrandId()));
			ItemBatch itemBatch = itemBatchService.queryItemBatchBySkuAndItemNo(car.getSku(), car.getItemNo());
			if(itemBatch!=null) {
				pageItemVo.setRetailPrice(itemVo.getRetailPrice());
				pageItemVo.setPlatformPrice(itemBatch.getPrice());
				pageItemVo.setSku(car.getSku());
				pageItemVo.setItemBatchNo(itemBatch.getBatchNo());
				totalItemFee=totalItemFee.add(itemBatch.getPrice().multiply(new BigDecimal(car.getNum())));
			}
			pageItemList.add(pageItemVo);
			ItemNumVo  itemNumVo=new ItemNumVo();
			itemNumVo.setItemId(itemVo.getId());
			itemNumVo.setItemNo(itemVo.getItemNo());
			itemNumVo.setNum(car.getNum());
			itemNumVo.setSku(car.getSku());
			list.add(itemNumVo);
		}
	    
	    UserAddressVo userAddressVo = userAddressService.queryDefaultAddress(userId);
		if (userAddressVo != null) {
			orderPayPageVO.setDefaultAddressTag(1);
			orderPayPageVO.setAddressId(userAddressVo.getId());
			orderPayPageVO.setUserDefaultAddressVo(userAddressVo);
		}
	    orderPayPageVO.setTotalItemFee(totalItemFee);
	    orderPayPageVO.setType("2");
	    orderPayPageVO.setPageItemVoList(pageItemList);
	    orderPayPageVO.setTransportFee(new BigDecimal(10));
		BigDecimal totalFee = totalItemFee.add(orderPayPageVO.getTransportFee());
		orderPayPageVO.setTotalFee(totalFee);
		orderPayPageVO.setCouponTag(couponTag(userId, list));
		orderPayPageVO.setTotalNum(orderPayPageVO.getPageItemVoList().size());
		return orderPayPageVO;
	}

	@Override
	public OrderPayPageVO payPageByOrder(Integer userId, String orderId) {
		OrderPayPageVO vo=new OrderPayPageVO();
		vo.setType("3");
		Order order=orderMapper.selectById(orderId);
		//是否有默认地址
		UserAddressVo userAddressVo = userAddressService.queryDefaultAddress(userId);
		if (userAddressVo != null) {
			vo.setDefaultAddressTag(1);
			vo.setUserDefaultAddressVo(userAddressVo);
		}
		
		List<OrderItemListVo>  orderItemList=orderInfoService.orderItemList(userId, orderId);
		List<PageItemVo> pageItemVoList=new ArrayList<>();
		List<ItemNumVo> list=new ArrayList<>();
		
		for(OrderItemListVo orderItemListVo:orderItemList) {
			PageItemVo pageItemVo=new PageItemVo();
			BeanUtil.copyProperties(orderItemListVo, pageItemVo);
			pageItemVoList.add(pageItemVo);
			// 是否有可用优惠券标识
			ItemNumVo itemNumVo=new ItemNumVo();
			itemNumVo.setItemId(orderItemListVo.getItemId());
			itemNumVo.setItemNo(orderItemListVo.getItemNo());
			itemNumVo.setNum(orderItemListVo.getItemNum());
			itemNumVo.setSku(orderItemListVo.getSku());
			list.add(itemNumVo);
		}
		vo.setCouponTag(couponTag(userId, list));
		
		return null;
	}


}
