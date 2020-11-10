package cn.jdcloud.medicine.mall.api.biz.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.promotion.service.GroupInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionInfoService;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.GroupInfoVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionInfoVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.promotion.GroupInfoMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfo;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoDto;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoResult;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfo;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoResult;

/**
 * @author chenQF
 * @desc 拼团记录业务层
 * @date 2020/8/24 0024 13:53
 */
@Service
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements GroupInfoService {

	@Resource
	GroupInfoMapper groupInfoMapper;

	@Resource
	PromotionInfoService promotionInfoService;
	@Resource
	private PromotionInfoMapper promotionInfoMapper;

	@Override
	public Page<GroupInfoResult> listGroupInfo(Page<GroupInfoResult> page, GroupInfoDto dto) {
		page.setRecords(groupInfoMapper.listGroupInfo(page, dto));
		page.setTotal(groupInfoMapper.listCount(dto));
		return page;
	}

	@Override
	public void updateGroupInfo(Integer promotionId) {
		// 查询活动规则
		PromotionInfoResult p = promotionInfoService.getOne(promotionId);
		// 查询团购中状态的
		Map map = new HashMap<>();
		map.put("promotion_id", promotionId);
		map.put("status", 1);
		List<GroupInfo> groupInfos = groupInfoMapper.selectByMap(map);
		if (groupInfos.size() > 0) {
			// 更新拼团记录状态，如果未达到人数的修改状态为活动结束未完成
			for (int i = 0; i < groupInfos.size(); i++) {
				// 如果是按人数 对比人数，如果是数量对比数量
				if (p.getGroupCondition() == 1) { // 1:按参团人数
					if (groupInfos.get(i).getUserNum() < p.getMinSuccessNum()) {
						groupInfos.get(i).setStatus(4);
					}
				}
				if (p.getGroupCondition() == 2) { // 2:按成交数量
					if (groupInfos.get(i).getItemNum() < p.getMinSuccessNum()) {
						groupInfos.get(i).setStatus(4);
					}
				}
				groupInfos.get(i).setEndTime(new Date());
				groupInfoMapper.updateById(groupInfos.get(i));
			}
		}
	}

	@Override
	public PromotionInfoVo queryPromotionInfoByGroupInfoId(Integer groupInfoId) {
		PromotionInfoVo promotionInfoVo = new PromotionInfoVo();
		GroupInfo groupInfo = groupInfoMapper.selectById(groupInfoId);
		if (groupInfo != null) {
			GroupInfoVo groupInfoVo = new GroupInfoVo();
			BeanUtil.copyProperties(groupInfo, groupInfoVo);
			Integer promotionId = groupInfo.getPromotionId();
			PromotionInfo promotionInfo = promotionInfoMapper.selectById(promotionId);
			if (promotionInfo != null) {
				PromotionVo promotionVo = new PromotionVo();
				BeanUtil.copyProperties(promotionInfo, promotionVo);
				promotionInfoVo.setPromotionVo(promotionVo);
			}
			
			
			
			promotionInfoVo.setGroupInfoVo(groupInfoVo);
			return promotionInfoVo;
		}
		return null;
	}

	@Override
	public GroupInfo queryGroupInfoByPromotionId(Integer promotionId) {
	  List<GroupInfo> list=groupInfoMapper.selectList
				(new QueryWrapper<GroupInfo>().eq("promotion_id", promotionId));
		if(list.size()>0) {
			return list.get(0);
		}
	  return null;
	}

}
