package cn.jdcloud.medicine.mall.api.biz.order.service.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;

import cn.jdcloud.framework.core.constant.Constants;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.code.AdminCode;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponRecordService;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.ItemNumVo;
import cn.jdcloud.medicine.mall.api.biz.integral.service.IntegralService;
import cn.jdcloud.medicine.mall.api.biz.order.code.OrderCode;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderItemDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OperateDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OrderListDto;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderInfoService;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderService;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderStatusLogService;
import cn.jdcloud.medicine.mall.api.biz.order.utils.OrderUtil;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderAddressVo;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderItemListVo;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderListVo;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ProductCode;
import cn.jdcloud.medicine.mall.api.biz.product.excel.ItemExcel;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.code.PromotionCode;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupUserService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.api.common.utils.MyExcelUtil;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.dao.coupon.CouponRecordMapper;
import cn.jdcloud.medicine.mall.dao.coupon.OrderCouponMapper;
import cn.jdcloud.medicine.mall.dao.order.OrderInfoMapper;
import cn.jdcloud.medicine.mall.dao.order.OrderMapper;
import cn.jdcloud.medicine.mall.dao.product.CarMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemBatchMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.promotion.GroupInfoMapper;
import cn.jdcloud.medicine.mall.dao.promotion.GroupUserMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionGroupItemMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionGroupMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.dao.user.UserAddressMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecord;
import cn.jdcloud.medicine.mall.domain.coupon.OrderCoupon;
import cn.jdcloud.medicine.mall.domain.integral.Integral;
import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.order.OrderInfo;
import cn.jdcloud.medicine.mall.domain.order.OrderStatusLog;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderTabCountVo;
import cn.jdcloud.medicine.mall.domain.product.Car;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfo;
import cn.jdcloud.medicine.mall.domain.promotion.GroupUser;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroup;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroupItem;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import cn.jdcloud.medicine.mall.domain.user.User;
import cn.jdcloud.medicine.mall.domain.user.UserAddress;

/**
 * @date 2018/9/12 14:18
 */

@Service("OrderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    UserMapper userMapper;
    @Resource
    ItemMapper itemMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderInfoMapper orderInfoMapper;
    @Resource
    CarMapper carMapper;
    @Resource
    GroupInfoService groupInfoService;
    @Resource
    GroupUserService  groupUserService;
    @Resource
    OrderService orderService;
    @Resource
    OrderInfoService orderInfoService;
    @Resource
    OrderStatusLogService orderStatusLogService;
    @Resource
    ItemBatchService itemBatchService;
    @Resource
    ItemBatchMapper itemBatchMapper;
    
    @Resource
    CouponRecordService couponRecordService;
    @Resource
    CouponRecordMapper couponRecordMapper;
    @Resource
    ItemService itemService;
    @Resource
    OrderCouponMapper orderCouponMapper;
    @Resource
    UserAddressMapper userAddressMapper;
    @Resource
    PromotionGroupMapper promotionGroupMapper;
    @Resource
    PromotionInfoMapper promotionInfoMapper;
    @Resource
    PromotionGroupItemMapper  promotionGroupItemMapper;
    @Resource
    GroupInfoMapper  groupInfoMapper;
    @Resource
    GroupUserMapper groupUserMapper;
    @Resource
    IntegralService integralService;
	@Autowired
	private PromotionGroupItemService promotionGroupItemService;

    /**
     * 创建拼团订单
     * @param createOrderDto
     * @return
     */
    private String createPromotionOrder(User buyer,CreateOrderDto createOrderDto) {
    	Integer userId=buyer.getId();
    	Integer promotionId=createOrderDto.getPromotionId();
    	// 活动校验
    	checkPromotion(promotionId);
    	PromotionGroupItem promotionGroupItem=getPromotionGroupItemByPromotionId(promotionId);
    	String itemNo=promotionGroupItem.getItemNo();
    	Integer itemNum=promotionGroupItem.getItemNum() * createOrderDto.getNum();
    	BigDecimal itemGroupPrice=promotionGroupItem.getItemGroupPrice();
    	// ItemBatch itemBatch=itemBatchService.queryItemBatchBySkuAndItemNo(promotionGroupItem.getSku(), itemNo);
    	Item item= itemService.queryItemByItemNo(itemNo);
        String orderId = OrderUtil.randomOrderId();
        // 订单项处理
    	OrderInfo of = new OrderInfo();
    	Date now=new Date();
        of.setId(0);
        of.setOrderId(orderId);
        of.setUserId(userId);
        of.setItemId(item.getId());
        of.setItemName(item.getItemName());
        //of.setSku(itemBatch.getSku());
        //of.setItemBatchNo(itemBatch.getBatchNo());
        of.setItemIcon(item.getImgCover());
        of.setItemNum(itemNum);
        of.setItemPrice(itemGroupPrice);
        of.setTotalPrice(of.getItemPrice().multiply(new BigDecimal(itemNum)));
        of.setCreateTime(now);
        orderInfoMapper.insert(of);

        //订单处理
        Order order = new Order();
        order.setOrderId(orderId);
        order.setPromotionId(promotionId);
        order.setOrderType(createOrderDto.getOrderType());
        order.setUserId(userId);
        order.setUserName(buyer.getCompanyName());
        order.setTotalNum(itemNum);
        order.setOrderStatus(Order.STATUS_DFK);
        order.setUserRemark(createOrderDto.getRemark());
        order.setCreateTime(now);
        order.setUpdateTime(now);
        //  订单地址处理
        order.setReceiveAddressId(createOrderDto.getAddreddId());
        dealOrderAddress(order,createOrderDto.getAddreddId());
        //相关费用处理
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setTransportFee(new BigDecimal(0));
        // 总费用就是商品价格加上运费
        order.setTotalAmount(of.getTotalPrice().add(order.getTransportFee()));
        order.setPaymentAmount(order.getTotalAmount());
    	orderMapper.insert(order);

	   //  生成相关的拼团表
//	    PromotionItem promotionItem=promotionGroupItemService.promotionItemOne(promotionId, itemNo);
//		PromotionInfo promotionInfo=promotionInfoMapper.selectById(promotionId);
		GroupInfo groupInfo=groupInfoService.queryGroupInfoByPromotionId(promotionId);
		// 拼团用户关系表
		GroupUser groupUser=new GroupUser();
		groupUser.setId(0);
		groupUser.setGroupId(groupInfo.getId());
		groupUser.setIsPay(1);
		groupUser.setCreateTime(now);
		groupUser.setUserId(userId);
		groupUser.setOrderId(orderId);
		groupUserMapper.insert(groupUser);
    	return orderId;
    }

    private GroupInfo getGroupInfo(Integer userId,Date date) {
    	List<GroupInfo> list=groupInfoMapper.selectList(new QueryWrapper<GroupInfo>()
    			.eq("plush_user_id", userId).eq("create_time", date));
    	if(list.size()>0) {
    		return list.get(0);
    	}
		return null;
    }

    private PromotionGroupItem getPromotionGroupItemByPromotionId(Integer promotionId) {
    	List<PromotionGroupItem> list=promotionGroupItemMapper.selectList(new QueryWrapper<PromotionGroupItem>()
    			.eq("promotion_id", promotionId));
    	if(list.size()>0) {
    		return list.get(0);
    	}
    	return null;
    }

    private void checkPromotion(Integer promotionId) {
    	PromotionInfo promotionInfo=promotionInfoMapper.selectById(promotionId);
    	Byte status =promotionInfo.getStatus();
    	Date now=new Date();
    	if(1!=status) {
    		//  当前活动无效
    		throw new ApiException(PromotionCode.PROMOTION_STATUS_ERROR);
    	}
    	if(promotionInfo.getEndTime().compareTo(now)<1) {
    		//  活动已结束
    		throw new ApiException(PromotionCode.PROMOTION_FINISHED);
    	}
    	PromotionGroup promotionGroup= promotionGroupMapper.selectById(promotionInfo.getRuleId());
    	// 状态是否可用
    	Integer groupStatus=promotionGroup.getGroupStatus();
    	if(1!=groupStatus) {
    	    // 当前活动模板状态不可用
    		throw new ApiException(PromotionCode.PROMOTION_TEMPLATE_STATUS_ERROR);
    	}
    	// 数量判断 成团条件（1:按参团人数;2:按成交数量）
    		Integer  groupCondition=promotionGroup.getGroupCondition();
    		GroupInfo groupInfo=groupInfoService.queryGroupInfoByPromotionId(promotionId);
    		Integer userNum=groupInfo.getUserNum();
    		Integer itemNum=groupInfo.getItemNum();
    		// 等于-1表示数量无限制
    		if(promotionGroup.getMaxGroupNum()!=-1) {
    			if(groupCondition==1 && userNum >=  promotionGroup.getMaxGroupNum()) {
    				//  活动人数已满
    				throw new ApiException(PromotionCode.PROMOTION_USER_NUM_OVERFLOW);
    			}
    			else  if(groupCondition==2 && itemNum >=  promotionGroup.getMaxGroupNum()) {
    				//  当前成交数量已达最大值
    				throw new ApiException(PromotionCode.PROMOTION_ITEM_NUM_OVERFLOW);
    			}
    		}
    	}



    @Override
    @Transactional
    public String createOrder(Integer userId,CreateOrderDto createOrderDto) {
        //参数校验
        List<CreateOrderItemDto> list = createOrderDto.getList();
        if(createOrderDto.getOrderType()==0&&CollectionUtils.isEmpty(list)){
            throw new ApiException(OrderCode.ORDER_PRODUCT_EMPTY);
        }
        User buyer = userMapper.selectById(userId);
        if(buyer==null ){
            logger.error("用户不存在:buyerId="+userId);
            throw  new ApiException(AdminCode.ADMIN_IS_NOT_EXIST);
        }
        if(createOrderDto.getOrderType()==1) {
        	return createPromotionOrder(buyer, createOrderDto);
        }
        String orderId = OrderUtil.randomOrderId();
        //生成订单明细
        Date now = new Date();
        int totalNum = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderInfo> orderInfos = new ArrayList<>();
        // 用于进行优惠券相关处理
        List<ItemNumVo> itemNumList =new ArrayList<>();
        for (CreateOrderItemDto cto:list) {
        	// 如果购物车参数不为空 则删除购物车
        	Integer carId=cto.getCarId();
        	if(carId!=null) {
        		Car car=carMapper.selectById(carId);
        		if(car!=null) {
        			car.setIsDelete(1);
        			car.setUpdateTime(new Date());
        			carMapper.updateById(car);
        		}
        	}
            Item p = itemMapper.selectById(cto.getItemId());
            //已删除或者已下架的商品
            if(p==null || p.getIsDeleted()== Constants.COMMON_IS || p.getState() == 1){
                throw new ApiException(ProductCode.PRODUCT_OFF_SELL);
            }
            OrderInfo of = new OrderInfo();
            of.setId(0);
            of.setOrderId(orderId);
            of.setUserId(userId);
            of.setItemId(p.getId());
            of.setItemName(p.getItemName());
            of.setSku(cto.getSku());
            of.setItemBatchNo(cto.getItemBatchNo());
            of.setItemIcon(p.getImgCover());
            of.setItemNum(cto.getNum());
            of.setItemPrice(getItemPirce(p.getItemNo(), cto.getSku()));
            of.setTotalPrice(of.getItemPrice().multiply(new BigDecimal(of.getItemNum())));
            of.setCreateTime(now);
            orderInfos.add(of);
            totalNum += of.getItemNum();
            totalAmount = totalAmount.add(of.getTotalPrice());
            ItemNumVo  itemNumVo=new ItemNumVo();
            itemNumVo.setItemId(p.getId());
            itemNumVo.setItemNo(p.getItemNo());
            itemNumVo.setNum(cto.getNum());
            itemNumVo.setSku(cto.getSku());
            itemNumList.add(itemNumVo);
        }
        //保存 订单明细 orderInfo
        orderInfoService.saveBatch(orderInfos);
        //生成订单 order
        Order order = new Order();
        order.setDeliveryMethod((byte)3);
        order.setOrderId(orderId);
        order.setOrderType(createOrderDto.getOrderType());
        order.setUserId(userId);
        order.setUserName(buyer.getCompanyName());
        order.setTotalNum(totalNum);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(Order.STATUS_DFK);
        order.setUserRemark(createOrderDto.getRemark());
        order.setCreateTime(now);
        order.setUpdateTime(now);
        //  订单地址处理
        order.setReceiveAddressId(createOrderDto.getAddreddId());
        dealOrderAddress(order,createOrderDto.getAddreddId());
        //  优惠券相关处理：
        order.setDiscountAmount(BigDecimal.ZERO);
        Integer couponId=createOrderDto.getUserCouponId();
        if(couponId!=null) {
        	// 说要使用的优惠券 则需要进行优惠券合法判断
        	CouponVo couponVo=	checkCoupon(couponId, couponId, itemNumList);
        	if(couponVo==null) {
        		// 优惠券使用不合法
        		throw new ApiException(OrderCode.ORDER_COUPON_ERROR);
        	}
        	else {
        		order.setDiscountAmount(couponVo.getDiscountAmount());
        		// 修改优惠券状态
        		CouponRecord couponRecord=couponRecordMapper.selectById(couponId);
        		couponRecord.setCouponStatus((byte) 3);
        		couponRecordMapper.updateById(couponRecord);
        		// 插入orderCoupon记录
        		OrderCoupon orderCoupon=new OrderCoupon();
        		orderCoupon.setId(0);
        		orderCoupon.setCreateTime(now);
        		orderCoupon.setUpdateTime(now);
        		orderCoupon.setOrderId(orderId);
        		orderCoupon.setDiscoutAmount(couponVo.getDiscountAmount());
        		orderCoupon.setUserCouponId(couponId);
        		orderCouponMapper.insert(orderCoupon);
        	}
        }
        order.setTransportFee(new BigDecimal(10));
        // 实际支付费用 商品总价-优惠券金额+运费
        order.setPaymentAmount(order.getTotalAmount().subtract(order.getDiscountAmount()).add(order.getTransportFee()));
        orderMapper.insert(order);
        return orderId;
    }


    // 订单地址处理
    private void dealOrderAddress(Order order,Integer addressId) {
    	UserAddress userAddress=userAddressMapper.selectById(addressId);
    	order.setAddress(userAddress.getAddress());
        order.setCityId(userAddress.getCityId());
		order.setCityName(userAddress.getCityName());
		order.setProvinceId(userAddress.getProvinceId());
		order.setProvinceName(userAddress.getProvinceName());
		order.setDistrictId(userAddress.getDistrictId());
		order.setDistrictName(userAddress.getDistrictName());
		order.setRealName(userAddress.getRealName());
		order.setMobile(userAddress.getMobile());
    }

    // 校验优惠券是否可用  可用 返回优惠券信息 不可以返回null
    private CouponVo checkCoupon(Integer userId,Integer couponId,List<ItemNumVo> itemNumList) {
    	List<CouponVo> couponList=couponRecordService.listCouponRecordByUserIdAndItemNos(userId, itemNumList);
    	for(CouponVo couponVo:couponList) {
    		if(couponId.equals(couponVo.getId())) {
    			return couponVo;
    		}
    	}
    	return null;
    }


    private BigDecimal getItemPirce(String itemNo,String sku) {
    	ItemBatch itemBatch=itemBatchService.queryItemBatchBySkuAndItemNo(sku, itemNo);
    	if(itemBatch!=null) {
    		return itemBatch.getPrice();
    	}
    	return BigDecimal.ZERO;
    }

    @Override
    public OrderTabCountVo orderCount(OrderDto dto){
        Map<String, Object> map = new HashMap<>();
        map.put("searchValue", dto.getSearchValue());
        map.put("startDate", dto.getStartDate());
        map.put("endDate", dto.getEndDate());
        return orderMapper.selectTabCount(map);
    }

    @Override
    public Page orderList(Page page, OrderDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderStatus", dto.getOrderStatus());
        map.put("searchValue", dto.getSearchValue());
        map.put("startDate", dto.getStartDate());
        map.put("endDate", dto.getEndDate());
        return orderMapper.selectOrderPage( page, map);
    }

    @Override
    public List<OrderInfoVO> getInfoByOrderId(String orderId) throws Exception{
        if (StringUtils.isBlank(orderId)) {
            throw new ApiException(OrderCode.ORDER_PARAM_ERROR);
        }
        return orderInfoMapper.getInfoByOrderId(orderId);
    }

    @Override
    public boolean receive(HttpServletRequest request, OperateDto dto) {
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        String id = request.getSession().getId();
        /*查询 是否开户 如果未开户 不允许接收 */
        //todo：查询开户
        /*查询是否具备资质
        如果不具备资质  状态变成  审核资质
        如果具备资质    状态变成   正在开单 */
        //todo：查询资质
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
        //todo:  如果具备资质  orderstatus 传 正在开单 STATUS_ZZKD
        updateWrapper.set("order_status",Order.STATUS_SHZZ);
        updateWrapper.in("order_id",Arrays.asList(dto.getOrderIds()));
        return orderService.update(updateWrapper);
    }

    /**
     * 界面点击 已开户 调此接口 将订单状态修改为正在开单
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean starting(HttpServletRequest request, OperateDto dto) {
        List<String> ids = Arrays.asList(dto.getOrderIds());
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        Integer id = userSession.getUserId();
        String adminName = userSession.getUserName();
        ids.forEach(x->{
            orderStatusLogService.save(new OrderStatusLog(x,id,adminName,"已开户",new Date()));
        });
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
        updateWrapper.set("order_status",Order.STATUS_ZZKD);
        updateWrapper.in("order_id",ids);
        return orderService.update(updateWrapper);
    }

    /**
     * 界面点击 开单完成 调此接口 将订单状态修改为分拣中
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean started(HttpServletRequest request, OperateDto dto) {
        List<String> ids = Arrays.asList(dto.getOrderIds());
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        Integer id = userSession.getUserId();
        String adminName = userSession.getUserName();
        ids.forEach(x->{
            orderStatusLogService.save(new OrderStatusLog(x,id,adminName,"开单完成 ",new Date()));
        });
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
        updateWrapper.set("order_status",Order.STATUS_FJZ);
        updateWrapper.in("order_id",ids);
        return orderService.update(updateWrapper);
    }

    /**
     * 界面点击 备货完成 调此接口 将订单状态修改为待配送
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean ready(HttpServletRequest request, OperateDto dto) {
        List<String> ids = Arrays.asList(dto.getOrderIds());
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        Integer id = userSession.getUserId();
        String adminName = userSession.getUserName();
        ids.forEach(x->{
            orderStatusLogService.save(new OrderStatusLog(x,id,adminName,"备货完成 ",new Date()));
        });
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
        updateWrapper.set("order_status",Order.STATUS_DPS);
        updateWrapper.in("order_id",ids);
        return orderService.update(updateWrapper);
    }

    /**
     * 界面点击 配送完成 调此接口 将订单状态修改为配送完成
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean delivered(HttpServletRequest request, OperateDto dto) {
        List<String> ids = Arrays.asList(dto.getOrderIds());
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        Integer id = userSession.getUserId();
        String adminName = userSession.getUserName();
        ids.forEach(x->{
            orderStatusLogService.save(new OrderStatusLog(x,id,adminName,"配送完成",new Date()));
        });
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
        updateWrapper.set("order_status",Order.STATUS_DDJS_ZCJS);
        updateWrapper.in("order_id",ids);
        return orderService.update(updateWrapper);
    }

    /**
     * 界面点击 发物流快递 调此接口 将订单状态修改为配送中
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean sendLogistic(HttpServletRequest request, OperateDto dto) {
        String orderId = dto.getOrderIds()[0];
        final String[] expressNo = dto.getExpressNo();
        final String expressCompanyId = dto.getExpressCompanyId();
        UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        Integer id = userSession.getUserId();
        String adminName = userSession.getUserName();
        orderStatusLogService.save(new OrderStatusLog(orderId,id,adminName,"发物流快递",new Date()));
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status",Order.STATUS_PSZ);
        if (CollectionUtils.isNotEmpty(Arrays.asList(expressNo)) && StringUtils.isNotBlank(expressCompanyId)) {
            String toJson = new Gson().toJson(expressNo);
            updateWrapper.set("express_no", toJson);
            updateWrapper.set("express_company_id", expressCompanyId);
        }else {
            throw new ApiException(OrderCode.ORDER_PARAM_ERROR);
        }
        updateWrapper.eq("order_id",orderId);
        return orderService.update(updateWrapper);
    }

    @Override
    public void export(OrderDto dto, HttpServletResponse response) throws Exception {

    }

    @Override
    public void template(HttpServletResponse response) throws Exception {
        String filename = "订单导入模板.xls";
        Map<String, String[]> guideOptions = this.getGuideOptions();
        OutputStream out = this.getDownloadStream(response, filename);
        new MyExcelUtil<ItemExcel>().importTemplate(out, guideOptions, ItemExcel.class);
    }

    /**
     * 获取下载输出流
     *
     * @param response
     * @param filename
     * @return java.io.OutputStream
     * @author HuZhengYu
     * @date 17:19 2020/8/21
     */
    private OutputStream getDownloadStream(HttpServletResponse response, String filename) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/octet-stream;charset=UTF-8");
        return response.getOutputStream();
    }

    /**
     * 获取导出模板下拉选项
     *
     * @return java.util.Map<java.lang.String,java.lang.String[]>
     * @author HuZhengYu
     * @date 18:59 2020/8/21
     */
    private Map<String, String[]> getGuideOptions() {
        HashMap<String, String[]> map = new HashMap<>();

        return map;
    }


	@Override
	public List<Order> orderList(OrderListDto orderListDto) {
		QueryWrapper<Order> queryWrapper =new QueryWrapper<Order>();
		if(orderListDto.getOrderId()!=null) {
			queryWrapper.eq("order_id", orderListDto.getOrderId());
		}
		else if(orderListDto.getUserId()!=null) {
			queryWrapper.eq("user_id", orderListDto.getUserId());
		}
		else if(orderListDto.getOrderStatus()!=null) {
			queryWrapper.eq("order_status", orderListDto.getOrderStatus());
		}
		else if(orderListDto.getOrderIds()!=null &&orderListDto.getOrderIds().size()>0) {
			queryWrapper.in("order_id", orderListDto.getOrderIds());
		}
		return orderMapper.selectList(queryWrapper);

	}


	@Override
	public List<OrderListVo> pageOrderList(int pageNum, int pageSize, Integer userId,String orderId, Byte orderState) {
		List<OrderListVo> list=new ArrayList<>();
		Page<Order> page=new Page<Order>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		QueryWrapper<Order> queryWrapper =new QueryWrapper<Order>();
		queryWrapper.eq(userId!=null,"user_id", userId);
		queryWrapper.eq(orderState!=null,"order_status", orderState);
		queryWrapper.eq(orderId!=null,"order_id", orderId);
		queryWrapper.orderByDesc("create_time");
		page=orderMapper.selectPage(page, queryWrapper);
		List<Order> orderList=page.getRecords();
		for(Order order:orderList) {
			OrderListVo  orderListVo=new OrderListVo();

			OrderAddressVo orderAddressVo=new OrderAddressVo();
			BeanUtil.copyProperties(order, orderAddressVo);
			orderListVo.setOrderAddressVo(orderAddressVo);
			orderListVo.setOrderType(order.getOrderType());
			orderListVo.setOrderStatus(order.getOrderStatus());
			orderListVo.setDeliveryMethod(order.getDeliveryMethod());
			orderListVo.setDeliveryMethodDesc(changeDeliveryMethod(order.getDeliveryMethod()));
			orderListVo.setDeliveryTime(order.getDeliveryTime());
			orderListVo.setDiscountAmount(order.getDiscountAmount());
			orderListVo.setSurplusPayTime(getSurplusPayTime(order.getCreateTime()));
			orderListVo.setSurplusReceivingTime(getSurplusReceivingTime(order.getDeliveryTime()));
			orderListVo.setTotalAmount(order.getTotalAmount());
			orderListVo.setPaymentAmount(order.getPaymentAmount());
			orderListVo.setCreateTime(order.getCreateTime());
			orderListVo.setPayTime(order.getPayTime());
			orderListVo.setTransportFee(order.getTransportFee());
			orderListVo.setOrderStatusDesc(Order.getOrderStatusDesc(order.getOrderStatus()));
			orderListVo.setOrderId(order.getOrderId());
			List<OrderItemListVo>  orderItemList=orderInfoService.orderItemList(userId, order.getOrderId());
			orderListVo.setItemList(orderItemList);
			orderListVo.setTotalNum(orderItemList.size());
			list.add(orderListVo);
		}
		return list;
	}

	private String getSurplusPayTime(Date payTime) {
		return "23小时10分钟";
	}
	private String getSurplusReceivingTime(Date surplusReceivingTime) {
		return "23天10小时";
	}

	// 1 自提   2 商家直送  3 快递物流
	private String changeDeliveryMethod(Byte deliveryMethod) {
		if(deliveryMethod==null) {
			return "";
		}
		switch(deliveryMethod) {
		case 1:
			return "自提";
		case 2:
			return "商家直送";
		case 3:
			return "快递物流";
		}
		return "";
	}

	@Override
	@Transactional
	public String updateOrderStatus(Integer userId,String orderId, byte orderStatus) {
		Order order=orderMapper.selectById(orderId);
		if(order!=null) {
			if(order.getUserId()!=userId) {
			  // TODO 抛异常 非法操作
			}
			order.setOrderStatus(orderStatus);
			orderMapper.updateById(order);
			//  TODO  根据不同的订单状态需要做不同的处理 比如 修改为已支付状态时需要更新一堆数据
		}
		return null;
	}

	
	/**
	 * 支付后进行更新操作
	 */
	@Override
	@Transactional
	public void afterPayUpdate(String orderId) {
		// 获得订单信息
		OrderListVo  orderInfo=null;
		Integer userId=null;
		List<OrderListVo> list=this.pageOrderList(1, 1, null,orderId, null);
		if(list.size()>0) {
			orderInfo=list.get(0);
		}
		else {
			return;
		}
		 /**
		  * 1、更新订单状态
		  * 2、更新库存、已售
		  * 3、如果是促销活动订单 则需要更新对应的数据:
		  * 
		  *   GroupInfo：参团人数userNum 购买数量itemNum  判断团购状态status
		  *   GroupUser：支付状态isPay
		  *   PromotionInfo：返佣（暂时不做） 
		  * 
		  * 4、更新用户积分
		  */
		
		// 更新订单状态
		Order order=orderMapper.selectById(orderId);
		userId=order.getUserId();
		order.setOrderStatus(Order.STATUS_YFK_DCL);
		order.setPayTime(new Date());
		orderMapper.updateById(order);
		
		// 更新商品库存 已售
		List<OrderItemListVo> itemList=orderInfo.getItemList();
		for(OrderItemListVo itemVo:itemList) {
			//  商品Item 需要更新字段 
			//**  最后一次销售时间：lastSaleTime 库存数量：qty 总销售次数： subtotalSaleTimes 总销售数量：subtotalSaleNum*/
			Integer itemId=itemVo.getItemId();
			Item  item=itemMapper.selectById(itemId);
			item.setLastSaleTime(new Date());
			item.setQty(item.getQty().intValue()-itemVo.getItemNum()<=0 ? BigDecimal.ZERO : item.getQty().subtract(new BigDecimal(itemVo.getItemNum())));
			item.setSubtotalPurTimes(item.getSubtotalSaleTimes()+1);
			item.setSubtotalSaleNum(item.getSubtotalSaleNum()+itemVo.getItemNum());
			itemMapper.updateById(item);
			// 更新商品规格 库存数据 
			ItemBatch  itemBatch=itemBatchService.queryItemBatchBySkuAndItemNo(itemVo.getSku(), itemVo.getItemNo());
			itemBatch.setQty(itemBatch.getQty().intValue()-itemVo.getItemNum()<=0?BigDecimal.ZERO:itemBatch.getQty().subtract(new BigDecimal(itemVo.getItemNum())));
			itemBatchMapper.updateById(itemBatch);
			
			// 更新用户积分
			Integer integral=item.getIntegral();
			if(integral>0) {
				integralService.obtainIntegral(userId, Integral.INTEGRAL_TYPE_BUY_ITEM, itemId);
			}
			/**
			 * 1、更新订单状态
			 * 2、更新库存、已售
			 * 3、如果是促销活动订单 则需要更新对应的数据:
			 * 
			 *   GroupInfo：参团人数userNum 购买数量itemNum  判断团购状态status
			 *   GroupUser：支付状态isPay
			 *   PromotionInfo：返佣（暂时不做） 
			 */
			
			Integer promotionId=order.getPromotionId();
			if(order.getOrderType()==1 && promotionId!=null) {
				GroupInfo  groupInfo=groupInfoService.queryGroupInfoByPromotionId(promotionId);
				groupInfo.setUserNum(groupInfo.getUserNum()+1);
				groupInfo.setItemNum(groupInfo.getItemNum()+order.getTotalNum());
				// 判断是否成团
				PromotionInfo  promotionInfo=promotionInfoMapper.selectById(promotionId);
				PromotionGroup  promotionGroup=promotionGroupMapper.selectById(promotionInfo.getRuleId());
				//成团条件（1:按参团人数;2:按成交数量）
				Integer condition=promotionGroup.getGroupCondition();
				// 最低成团数量
				Integer minSuccessNum=promotionGroup.getMinSuccessNum();
				if(1==condition) {
					// 拼团成功
					if(groupInfo.getUserNum()>=minSuccessNum) {
						groupInfo.setStatus(2);
					}
				}
				else if(2==condition) {
					// 拼团成功
					if(groupInfo.getItemNum()>=minSuccessNum) {
						groupInfo.setStatus(2);
					}
				}
				// 更新groupInfo
				groupInfoMapper.updateById(groupInfo);
				GroupUser  groupUser=groupUserService.queryGroupUserByUserIdAndOrderId(userId,orderId);
				groupUser.setIsPay(3);
				groupUserMapper.updateById(groupUser);
			}
		}
		     
	}




}
