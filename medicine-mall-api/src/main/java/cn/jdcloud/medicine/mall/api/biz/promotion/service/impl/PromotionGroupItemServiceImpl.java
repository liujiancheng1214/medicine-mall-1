package cn.jdcloud.medicine.mall.api.biz.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.product.vo.IndexPromotionItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.dto.PromotionItemDto;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionItemUserVo;
import cn.jdcloud.medicine.mall.api.common.exception.CustomException;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionGroupItemMapper;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionGroupItem;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItem;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItemListDto;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItemUserDto;

@Service
public class PromotionGroupItemServiceImpl extends ServiceImpl<PromotionGroupItemMapper, PromotionGroupItem>
implements PromotionGroupItemService {

    @Resource
    private PromotionGroupItemMapper promotionGroupItemMapper;

    @Override
    public void updateByPromotionId(List<PromotionItemDto> promotionItems, Integer promotionId) {
        List<PromotionGroupItem> items = new ArrayList<>();
        Date time = new Date();
        ArrayList<String> itemNos = new ArrayList<>();
        promotionItems.forEach(item-> {
            String itemNo = item.getItemNo();
            if(itemNos.contains(itemNo)){
                throw new CustomException(552,"活动商品选择重复，保存失败！");
            }
            itemNos.add(itemNo);
            PromotionGroupItem groupItem = item.createGroup();
            groupItem.setPromotionId(promotionId);
            groupItem.setCreateTime(time);
            items.add(groupItem);

        });
        promotionGroupItemMapper.delete(new QueryWrapper<PromotionGroupItem>().eq("promotion_id",promotionId));
        promotionGroupItemMapper.insertBatch(items);
    }

    @Override
    public List<PromotionItem> PromotionGroupItemInfo(Integer promotionId) {
        return promotionGroupItemMapper.PromotionGroupItemInfo(promotionId);
    }

	@Override
	public PromotionItem promotionItemOne(Integer promotionId, String itemNo) {
		List<PromotionItem> list=PromotionGroupItemInfo(promotionId);
		for(PromotionItem promotionItem:list) {
			if(promotionItem.getItemNo().equals(itemNo)) {
				return promotionItem;
			}
		}
		return null;
	}

	/**
	 * 判断商品是否团购商品
	 */
	@Override
	public boolean checkItemIsPromotionItem(String itemNo) {
		int i=promotionGroupItemMapper.checkItemIsPromotionItem(itemNo);
		return i>0;
	}

	/**
	 * 查询团购列表
	 */
	@Override
	public List<ItemVo> listPromotionItems(String itemName, String itemBrandName, Integer sortType) {
//		List<PromotionItemListDto>list=promotionGroupItemMapper.listPromotionItems(null, itemName, itemBrandName, sortType, null);
//		List<ItemVo>  voList=new ArrayList<>();
//		for(PromotionItemListDto dto:list) {
//			voList.add(new ItemVo(dto) );
//		}
//		return voList;
		return null;
	}

	@Override
	public List<PromotionItemUserVo> listPromotionItemUser(Integer promotionId) {
		List<PromotionItemUserDto> list=promotionGroupItemMapper.listPromotionItemUser(promotionId);
		// 计算时间  手机中间四位****号代替
		List<PromotionItemUserVo> volist=new ArrayList<>();
		for(PromotionItemUserDto dto:list) {
			PromotionItemUserVo vo=new PromotionItemUserVo();
			vo.setName(dto.getName());
			vo.setMobile(replaceMobile(dto.getMobile()));
			vo.setBeforeTime(getBeforeTime(dto.getCreateTime()));
			volist.add(vo);
		}
		return volist;
	}
	private String replaceMobile(String mobile) {
		if(mobile==null) {
			return "";
		}
		mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
		return mobile;
	}
	
	private String getBeforeTime(Date date) {
		long currentTime=System.currentTimeMillis();
		long createTime=date.getTime();
		long sub=currentTime-createTime;
		// 小于一个小时
		if(sub<60*3600) {
			long minute= sub/3600;
			return minute+"分钟以前";
		}
		else {
			long hour=sub/(3600*60);
			return hour+"小时以前";
		}
	}

	@Override
	public List<IndexPromotionItemVo> listPromotionItemVo(Integer userId, int pageNum, int pageSize,String searchValue, int type) {
		List<PromotionItemListDto>list=promotionGroupItemMapper.listPromotionItems(null,null,null,null);
		List<IndexPromotionItemVo> voList=new ArrayList<>();
		for(PromotionItemListDto  dto:list) {
			IndexPromotionItemVo itemVo=new IndexPromotionItemVo();
			BeanUtil.copyProperties(dto, itemVo);
			itemVo.setEffectiveDate(dto.getEffectiveDate());
			itemVo.setImgCover(dto.getImgCover());
			itemVo.setItemName(dto.getItemName());
			itemVo.setItemNo(dto.getItemNo());
			itemVo.setLimitNum(dto.getLimitNum());
			itemVo.setPromotionId(dto.getPromotionId());
			itemVo.setPromotionPrice(dto.getItemGroupPrice());
			itemVo.setSoldNum(dto.getSoldNum());
			itemVo.setSurplusTime(dto.getEndTime().getTime()-System.currentTimeMillis()/1000);
			voList.add(itemVo);
		}
		return voList;
	}
}
