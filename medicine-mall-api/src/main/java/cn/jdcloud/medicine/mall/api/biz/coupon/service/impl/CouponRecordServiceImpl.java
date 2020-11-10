package cn.jdcloud.medicine.mall.api.biz.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponRecordService;
import cn.jdcloud.medicine.mall.api.biz.coupon.utils.TimeUtil;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.ItemNumVo;
import cn.jdcloud.medicine.mall.api.biz.coupon.vo.UserCouponVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.coupon.CouponMapper;
import cn.jdcloud.medicine.mall.dao.coupon.CouponRecordMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponDto;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecord;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecordDto;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecordResult;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import cn.jdcloud.medicine.mall.domain.user.User;

/**
 * @author chenQF
 * @desc 优惠券业务层
 * @date 2020/8/20 0020 17:43
 */
@Service
@Transactional
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecord>
		implements CouponRecordService {

	@Resource
	CouponRecordMapper couponRecordMapper;

	@Resource
	CouponMapper couponMapper;

	@Resource
	UserMapper userMapper;
	@Resource
	private ItemService itemService;
	@Resource
	private ItemBatchService itemBatchService;

	@Override
	public Page<CouponRecordResult> listCouponRecord(Page page, CouponDto couponDto) {
		page.setRecords(couponRecordMapper.listCouponRecord(page, couponDto));
		page.setTotal(couponRecordMapper.listCount(couponDto));
		return page;
	}

	@Override
	public void update(CouponRecord record) {
		couponRecordMapper.updateById(record);
	}

	@Override
	public UserCouponVo listCouponRecordByUserIdAndStatus(Integer userId, String status) {
		Page page = new Page();
		page.setSize(Integer.MAX_VALUE);
		page.setCurrent(1);
		CouponDto couponDto = new CouponDto();
		couponDto.setUserId(userId + "");
		couponDto.setCouponStatus(status);
		Page<CouponRecordResult> couponRecordResult = listCouponRecord(page, couponDto);
		List<CouponRecordResult> resultList = couponRecordResult.getRecords();
		List<CouponVo> kyList = new ArrayList<>();
		List<CouponVo> gqList = new ArrayList<>();
		List<CouponVo> hxList = new ArrayList<>();
		UserCouponVo vo = new UserCouponVo();
		for (CouponRecordResult result : resultList) {
			CouponVo couponVo = changeCouponRecordResult(result);
			switch (couponVo.getCouponStatus()) {
			case 1:
				kyList.add(couponVo);
				break;
			case 2:
				gqList.add(couponVo);
				break;
			case 3:
				gqList.add(couponVo);
				break;
			}
		}
		vo.setGqList(gqList);
		vo.setKyList(kyList);
		//TODO  核销状态待确定
		//vo.setHxList(hxList);
		return vo;
	}

	private CouponVo changeCouponRecordResult(CouponRecordResult rouponRecordResult) {
		CouponVo couponVo = new CouponVo();
		Integer couponId = rouponRecordResult.getCouponId();
		Coupon coupon = couponMapper.selectById(couponId);
		BeanUtil.copyProperties(coupon, couponVo);
		couponVo.setCouponId(coupon.getId());
		couponVo.setId(rouponRecordResult.getId());
		// 有效开始时间
		couponVo.setEffectiveStratTime(rouponRecordResult.getCreateTime());
		// 有效结束时间
		couponVo.setEffectiveEndTime(rouponRecordResult.getExpireTime());
		// 状态
		couponVo.setCouponStatus(rouponRecordResult.getCouponStatus());
		couponVo.setTypeDesc(getTypeDesc(couponVo.getType()));
		return couponVo;
	}

	private String getTypeDesc(byte type) {
		switch(type) {
		case 1:
			return "代金券";
		case 2:
			return "折扣券";
		case 3:
			return "满减券";
		}
		return "";
	}
	
	/**
	 * 查询满足使用条件的优惠券列表
	 */
	@Override
	public List<CouponVo> listCouponRecordByUserIdAndItemNos(Integer userId, List<ItemNumVo> list) {
		// 计算出总金额
		BigDecimal totalPrice = new BigDecimal(0);
		// 所有的类别
		List<Integer> categoryIds = new ArrayList<>();
		// 所有的品牌
		List<Integer> brandIds = new ArrayList<>();
		// 所有的ItemId
		List<Integer> itemIds = new ArrayList<>();
		for (ItemNumVo itemNumVo : list) {
			Item itemVo = itemService.queryItemByItemNo(itemNumVo.getItemNo());
			Integer itemCategoryId = itemVo.getItemCategoryId();
			Integer itemBrandId = itemVo.getItemBrandId();
			ItemBatch itemBatch = itemBatchService.queryItemBatchBySkuAndItemNo(itemNumVo.getSku(),
					itemNumVo.getItemNo());
			if (itemBatch == null) {
				// 数据异常
				throw new ApiException(1000, "数据异常");
			}
			totalPrice=totalPrice.add(itemBatch.getPrice().multiply(new BigDecimal(itemNumVo.getNum())));
			categoryIds.add(itemCategoryId);
			brandIds.add(itemBrandId);
			itemIds.add(itemVo.getId());
		}
		// 先查询有效的优惠券列表
		UserCouponVo userCouponVo = listCouponRecordByUserIdAndStatus(userId, 1 + "");
		List<CouponVo> kyList = userCouponVo.getKyList();
		// 根据ItemNos 进一步筛选
		List<CouponVo> filterList=new ArrayList<>();
		for (CouponVo couponVo : kyList) {
			if(checkCoupon(couponVo, totalPrice, categoryIds, brandIds, itemIds, list)) {
				filterList.add(couponVo);
			}
		}
		return filterList;
	}

	// 判断优惠券是否满足可用条件
	private boolean checkCoupon(CouponVo couponVo, BigDecimal totalPrice, List<Integer> categoryIds,
			List<Integer> brandIds, List<Integer> itemIds, List<ItemNumVo> list) {
		Byte limitType = couponVo.getLimitType();
		String limitItemIds = null;
		List<String> tempIds = null;
		switch (limitType) {
		case 0:
			// 只需判断 总金额是否满足最低金额要求
			if (couponVo.getDiscountLimitAmount().subtract(totalPrice).intValue() < 0) {
				return true;
			}
			break;
		// 1 商品限制类型
		case 1:
			limitItemIds = couponVo.getLimitItenIds();
			tempIds = Arrays.asList(limitItemIds.split(","));
			for (Integer itemId : itemIds) {
				if (tempIds.contains(itemId.toString()) && couponVo.getLimitItemNum() <= getNum(list, itemId)) {
					return true;
				}
			}
			break;
		// 2 品牌限制
		case 2:
			limitItemIds = couponVo.getLimitItenIds();
			tempIds = Arrays.asList(limitItemIds.split(","));
			for (Integer brandId : brandIds) {
				if (tempIds.contains(brandId.toString())) {
					return true;
				}
			}
			break;
		// 3 类别限制
		case 3:
			limitItemIds = couponVo.getLimitItenIds();
			tempIds = Arrays.asList(limitItemIds.split(","));
			for (Integer categoryId : categoryIds) {
				if (tempIds.contains(categoryId.toString())) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	private int getNum(List<ItemNumVo> list, Integer itemId) {
		for (ItemNumVo vo : list) {
			if (vo.getItemId().equals(itemId)) {
				return vo.getNum();
			}
		}
		return 0;
	}

	// 处理折扣券
	private void dealDiscountCoupon(CouponVo couponVo, BigDecimal totalPrice, List<Integer> categoryIds,
			List<Integer> brandIds, List<Integer> itemIds, List<ItemNumVo> list) {

	}

	// 处理满减
	private void dealItemCoupon(CouponVo couponVo, BigDecimal totalPrice, List<Integer> categoryIds,
			List<Integer> brandIds, List<Integer> itemIds, List<ItemNumVo> list) {

	}

	@Override
	public void add(CouponRecordDto dto) {
		List<CouponRecord> list = new ArrayList<>();
		// 查询优惠券根据id
		Coupon coupon = couponMapper.selectById(dto.getCouponId());
		if (coupon == null) {
			throw new ApiException(1000, "发放失败，优惠券类别不存在");
		}
		if ("1".equals(dto.getSendType())) { // 0发给部分人 1发给所有人
			List<User> users = userMapper.selectList(null);
			if (users.size() > 0) {
				for (int i = 0; i < users.size(); i++) {
					dto.getUserIds().add(users.get(i).getId());
				}
			}
		}
		// for循环判断发放用户数量并封装记录对象
		if (dto.getUserIds().size() > 0) {
			for (int i = 0; i < dto.getUserIds().size(); i++) {
				CouponRecord record = new CouponRecord();
				record.setUserId(dto.getUserIds().get(i));
				record.setCouponId(coupon.getId());
				record.setCouponType(coupon.getType());
				record.setExpireTime(TimeUtil.dateAddDays(new Date(), coupon.getExpireDays()));
				record.setCouponStatus((byte) 1);
				record.setCreateTime(new Date());
				record.setUpdateTime(new Date());
				list.add(record);
			}
			int count = couponRecordMapper.insertCouponRecord(list);
			if (count <= 0) {
				throw new ApiException(1000, "发放失败，记录插入失败");
			}
			// 修改已使用优惠券数量
			coupon.setBudgetSendTotalQty(coupon.getBudgetSendTotalQty() + count);
			int num = couponMapper.updateById(coupon);
			if (num <= 0) {
				throw new ApiException(1000, "发放失败，类别更新失败");
			}
		}
	}
}
