package cn.jdcloud.medicine.mall.api.biz.share.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.coupon.utils.TimeUtil;
import cn.jdcloud.medicine.mall.api.biz.share.code.ShareCode;
import cn.jdcloud.medicine.mall.api.biz.share.service.ShareGroupService;
import cn.jdcloud.medicine.mall.api.biz.share.vo.ShareGroupUser;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.coupon.CouponMapper;
import cn.jdcloud.medicine.mall.dao.coupon.CouponRecordMapper;
import cn.jdcloud.medicine.mall.dao.share.ShareGroupMapper;
import cn.jdcloud.medicine.mall.dao.share.ShareMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.coupon.Coupon;
import cn.jdcloud.medicine.mall.domain.coupon.CouponRecord;
import cn.jdcloud.medicine.mall.domain.share.Share;
import cn.jdcloud.medicine.mall.domain.share.ShareGroup;
import cn.jdcloud.medicine.mall.domain.user.User;

@Service
public class ShareGroupServiceImpl implements ShareGroupService {

	@Autowired
	private ShareGroupMapper shareGroupMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ShareMapper shareMapper;
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private CouponRecordMapper couponRecordMapper;
	
	@Override
	@Transactional
	public void addShareGroup(Integer userId, Integer shareId) {
		//TODO 发起人和领取人不能为相同的人
		Share share=shareMapper.selectById(shareId);
		if(share!=null) {
			if(share.getSponsorId().equals(userId)) {
				  throw  new ApiException(ShareCode.SPONSOR_ERROR);
			}
		}
		Integer conponId=share.getConponId();
		List<ShareGroup> list=shareGroupMapper.selectList(new QueryWrapper<ShareGroup>().eq("share_id", shareId)
				.eq("user_id", userId));
		if(list.size()>0) {
			 throw  new ApiException(ShareCode.SHARE_GROUP_EXIST);
		}
		Coupon  coupon=couponMapper.selectById(share.getConponId());
		//如果list不为空 则说明发起人已经领取过一次了
		list=shareGroupMapper.selectList(new QueryWrapper<ShareGroup>()
				.eq("share_id", shareId));
		if(list.size()==0) {
			// 发起人领取优惠券
			CouponRecord sponsorRecord = new CouponRecord();
			sponsorRecord.setUserId(share.getSponsorId());
			sponsorRecord.setCouponId(share.getConponId());
			sponsorRecord.setCouponType(coupon.getType());
			sponsorRecord.setExpireTime(TimeUtil.dateAddDays(new Date(), coupon.getExpireDays()));
			sponsorRecord.setCouponStatus((byte) 1);
			sponsorRecord.setCreateTime(new Date());
			sponsorRecord.setUpdateTime(new Date());
			sponsorRecord.setType((byte)2);
			couponRecordMapper.insert(sponsorRecord);
		}
		
		ShareGroup shareGroup=new ShareGroup();
		shareGroup.setId(0);
		shareGroup.setCreateTime(new Date());
		shareGroup.setShareId(shareId);
		shareGroup.setUserId(userId);
		shareGroup.setConponId(conponId);
		shareGroupMapper.insert(shareGroup);
		//  领取人 增加对应的优惠券
		CouponRecord record = new CouponRecord();
		record.setUserId(userId);
		record.setCouponId(share.getConponId());
		record.setCouponType(coupon.getType());
		record.setExpireTime(TimeUtil.dateAddDays(new Date(), coupon.getExpireDays()));
		record.setCouponStatus((byte) 1);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setType((byte)2);
		couponRecordMapper.insert(record);
	}

	@Override
	public Page<ShareGroupUser> pageShareGroupUser(int pageNum, int pageSize) {
		Page<ShareGroup> page=new Page();
		page=shareGroupMapper.selectPage(page, new QueryWrapper<ShareGroup>().orderByDesc("create_time"));
		List<ShareGroup> list=page.getRecords();
		List<Integer> userIds=list.stream().map(bean->bean.getUserId()).collect(Collectors.toList());
		List<User> userList=new ArrayList<>();
		if(userIds.size()>0) {
			userList=userMapper.selectBatchIds(userIds);
		}
		List<ShareGroupUser> shareGroupUserList=new ArrayList<>();
		for(User  user:userList) {
			ShareGroupUser shareGroupUser=new ShareGroupUser();
			shareGroupUser.setHeadImg(user.getHeadImg());
			shareGroupUser.setName(user.getCompanyName());
			shareGroupUser.setTime(dealTime(list, user.getId()));
			shareGroupUserList.add(shareGroupUser);
		}
		Page<ShareGroupUser> pageInfo =new Page<ShareGroupUser>();
		BeanUtil.copyProperties(page, pageInfo, "records");
		pageInfo.setRecords(shareGroupUserList);
		return pageInfo;
	}

	private Date dealTime(List<ShareGroup> list,Integer userId) {
		for(ShareGroup  shareGroup:list) {
			if(shareGroup.getUserId().equals(userId)) {
				return shareGroup.getCreateTime();
			}
		}
		return new Date();
	}
}
