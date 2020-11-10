package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.admin.service.AdminService;
import cn.jdcloud.medicine.mall.api.biz.product.dto.ItemDto;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ItemBusinessEnum;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ItemImportantEnum;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ItemStopBuyEnum;
import cn.jdcloud.medicine.mall.api.biz.product.excel.ItemExcel;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemBatchVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PromotionItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.promotion.service.PromotionGroupItemService;
import cn.jdcloud.medicine.mall.api.biz.promotion.vo.PromotionItemUserVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.FootprintService;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserCollectionService;
import cn.jdcloud.medicine.mall.api.common.exception.CustomException;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.product.ItemBrandMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemCategoryMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemRecommendMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionGroupItemMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.domain.admin.Admin;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import cn.jdcloud.medicine.mall.domain.product.ItemRecommend;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoDto;
import cn.jdcloud.medicine.mall.domain.user.UserCollection;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    @Resource
    private ItemMapper itemMapper;
    @Resource
    ItemCategoryMapper itemCategoryMapper;
    @Resource
    ItemBrandMapper itemBrandMapper;
    @Resource
    ItemBatchService itemBatchService;
    @Resource
    AdminService adminService;
    @Resource
    private ItemRecommendMapper itemRecommendMapper;
    @Resource
    private PromotionGroupItemService promotionGroupItemService;
    @Resource
    private PromotionGroupItemMapper promotionGroupItemMapper;
    @Resource
    private PromotionInfoMapper promotionInfoMapper;
    @Resource
    private UserCollectionService userCollectionService;
    @Resource
    private FootprintService footprintService;
    /**
     * 查询商品推荐列表  1 标识查询首页推荐列表
     */
    @Override
	public List<ItemVo> listItemRecommend(String itemNo) {
    	QueryWrapper<ItemRecommend>  queryWrapper=new QueryWrapper();
    	queryWrapper.eq("item_no", itemNo).orderByAsc("sort");
    	List<ItemRecommend> recomendList=itemRecommendMapper.selectList(queryWrapper);
    	List<ItemVo> recommendList=new ArrayList<>();
    	for(ItemRecommend itemRecommend:recomendList) {
    		String recommendItemNo=itemRecommend.getRecommendItemNo();
    		ItemVo recommendItem=queryItemVoByItemNo(recommendItemNo);
    		recommendList.add(recommendItem);
    	}
		return recommendList;
	}


    @Override
	public ItemDetailVo queryItemDetail(Integer userId,String itemNo) {
    	ItemDetailVo  itemDetailVo=new ItemDetailVo();
    	ItemVo itemVo=queryItemVoByItemNo(itemNo);
    	itemDetailVo.setItemVo(itemVo);
    	//商品推荐表
    	List<ItemVo> recommendList=listItemRecommend(itemNo);
    	itemDetailVo.setRecommendList(recommendList);
    	 // 是否已收藏
    	UserCollection userCollection=userCollectionService.queryUserCollectionByUserIdAndItemNo(userId, itemNo);
		if(userCollection!=null) {
			itemDetailVo.setCollectionTag(1);
		}
    	return itemDetailVo;
	}


    /**
     * 商品列表分页
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public Page<ItemVo> page(Page<Item> page, QueryWrapper<Item> queryWrapper) {
        Page<Item> itemPage = itemMapper.selectPage(page, queryWrapper);
        Page<ItemVo> itemVoPage = new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());
        List<Item> items = itemPage.getRecords();
        List<ItemVo> simpleList=new ArrayList<>();
        if (items.size() != 0) {
            for (Item item:items){
                ItemVo simple=new ItemVo();
                BeanUtil.copyProperties(item, simple);
                if(promotionGroupItemService.checkItemIsPromotionItem(item.getItemNo())) {
                    simple.setPromotionTag(1);
                }
                simpleList.add(simple);
            }
        }
        itemVoPage.setRecords(simpleList);
        return  itemVoPage;
    }

    @Override
    public Page<ItemVo> webPage(Page<Item> page, QueryWrapper<Item> queryWrapper) {
        Page<Item> itemPage = itemMapper.selectPage(page, queryWrapper);
        Page<ItemVo> itemVoPage = new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());
        List<Item> items = itemPage.getRecords();
        if (items.size() != 0) {
            ArrayList<String> itemNos = new ArrayList<>();
            for (Item item:items){
                itemNos.add(item.getItemNo());
            }
            Map<String, List<ItemBatchVo>> listMap = itemBatchService.getGroupMapByNos(itemNos);
            List<ItemVo> records = new ArrayList<>();
            for (Item item:items){
                records.add(new ItemVo(item, listMap));
            }
            itemVoPage.setRecords(records);
        }
        return itemVoPage;
    }

    /**
     * 获取导出数据集合
     *
     * @param queryWrapper
     * @return
     */
    @Override
    public List<ItemExcel> exportList(QueryWrapper<Item> queryWrapper) {
        List<Item> items = itemMapper.selectList(queryWrapper);
        HashSet<Integer> userIds = new HashSet<>();
        items.forEach(item -> {
            userIds.add(item.getCreator());
            userIds.add(item.getUpdator());
            userIds.add(item.getAuditor());
        });
        Map<Integer, String> users = adminService.list(new QueryWrapper<Admin>().in("id", userIds)).stream()
                .collect(Collectors.toMap(Admin::getId, Admin::getName));
        List<ItemExcel> itemExports = items.stream().map(item -> new ItemExcel(item, users)).collect(Collectors.toList());
        return itemExports;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ItemDto dto, Integer userId) {
        Item item = dto.wrapItem();
        List<ItemBatch> itemBatch = dto.getItemBatch();
        String itemNo = item.getItemNo();
        BigDecimal qty = BigDecimal.ZERO;
        List<String> batchNos = new ArrayList<>();
        for (ItemBatch batch : itemBatch) {
            batch.setItemNo(itemNo);
            qty = qty.add(batch.getQty());
            String batchNo = batch.getBatchNo();
            if (batchNos.contains(batchNo)) {
                throw new CustomException(561, "商品库存批次号选择重复，保存失败！");
            }
            batchNos.add(batchNo);
        }
        Integer itemId = item.getId();
        List<Item> itemNos = itemMapper.selectList(new QueryWrapper<Item>().eq("item_no", item.getItemNo()));
        if (itemNos.size() != 0 && !itemNos.get(0).getId().equals(itemId)) {
            throw new CustomException(552, "商品编号已存在，保存失败！");
        }
        if (itemId == null) {
            item.setCreator(userId);
            item.setCreateTime(new Date());
        } else {
            if (item.getQty().intValue() <= 0) {
                item.setState((byte) 1);
            }
        }

        item.setUpdator(userId);
        item.setQty(qty);
        boolean saveOrUpdate = this.saveOrUpdate(item);
        if (saveOrUpdate) {
            itemBatchService.remove(new QueryWrapper<ItemBatch>().eq("item_no", itemNo));
            itemBatchService.insertBatch(itemBatch);
        }
        return saveOrUpdate;
    }

    /**
     * 获取导出模板下拉选项
     *
     * @return
     */
    @Override
    public Map<String, String[]> getGuideOptions() {
        HashMap<String, String[]> map = new HashMap<>(6);
        String[] itemCategorys = itemCategoryMapper.selectList(new QueryWrapper<ItemCategory>().eq("is_deleted", 0))
                .stream().map(ItemCategory::getCategoryName).toArray(String[]::new);
        String[] itemBrands = itemBrandMapper.selectList(new QueryWrapper<ItemBrand>().eq("is_deleted", 0))
                .stream().map(ItemBrand::getIbrandName).toArray(String[]::new);
        map.put("商品分类", itemCategorys);
        map.put("商品品牌", itemBrands);
        map.put("经营方式", ItemBusinessEnum.getNames());
        map.put("是否停购", ItemStopBuyEnum.getNames());
        map.put("重点商品", ItemImportantEnum.getNames());
        return map;
    }

    /**
     * 获取字典
     *
     * @return
     */
    @Override
    public Map<String, Map> getItemDict(boolean reverse) {
        HashMap<String, Map> map = new HashMap<>(6);
        List<ItemCategory> categoryList = itemCategoryMapper.selectList(new QueryWrapper<ItemCategory>().eq("is_deleted", 0));
        List<ItemBrand> brandList = itemBrandMapper.selectList(new QueryWrapper<ItemBrand>().eq("is_deleted", 0));
        if (reverse) {
            Map<Integer, String> itemCategorys = categoryList.stream().collect(Collectors.toMap(ItemCategory::getId, ItemCategory::getCategoryName));
            Map<Integer, String> itemBrands = brandList.stream().collect(Collectors.toMap(ItemBrand::getId, ItemBrand::getIbrandName));
            map.put("itemCategoryId", itemCategorys);
            map.put("itemBrandId", itemBrands);
            map.put("businessModel", ItemBusinessEnum.getReverseMap());
            map.put("stopBuy", ItemStopBuyEnum.getReverseMap());
            map.put("important", ItemImportantEnum.getReverseMap());
        } else {
            Map<String, Integer> itemCategorys = categoryList.stream().collect(Collectors.toMap(ItemCategory::getCategoryName, ItemCategory::getId));
            Map<String, Integer> itemBrands = brandList.stream().collect(Collectors.toMap(ItemBrand::getIbrandName, ItemBrand::getId));
            map.put("itemCategoryId", itemCategorys);
            map.put("itemBrandId", itemBrands);
            map.put("businessModel", ItemBusinessEnum.getMap());
            map.put("stopBuy", ItemStopBuyEnum.getMap());
            map.put("important", ItemImportantEnum.getMap());
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean importItems(List<ItemExcel> list, Integer userId) {
        List<Item> items = new ArrayList<>();
        List<String> itemNos = new ArrayList<>();
        Set<String> nos = new HashSet<>();
        Date date = new Date();
        list.forEach(item -> {
            Item pojo = item.createPojo();
            pojo.setUpdator(userId);
            pojo.setCreator(userId);
            pojo.setCreateTime(date);
            items.add(pojo);
            String itemNo = item.getItemNo();
            if (itemNos.contains(itemNo)) {
                nos.add(itemNo);
            } else {
                itemNos.add(itemNo);
            }
        });
        if (nos.size() != 0) {
            StringBuffer stringBuffer = new StringBuffer("以下商品编号在excel中重复请修改后重新导入：");
            nos.forEach(no -> stringBuffer.append(no).append("; "));
            throw new CustomException(551, stringBuffer.toString());
        }
        List<Item> repetition = itemMapper.selectList(new QueryWrapper<Item>().select("item_no").in("item_no", itemNos));
        if (repetition.size() != 0) {
            StringBuffer stringBuffer = new StringBuffer("数据库中已存在以下商品编号：");
            repetition.forEach(item -> stringBuffer.append(item.getItemNo()).append("; "));
            throw new CustomException(552, stringBuffer.toString());
        }
        itemMapper.batchInsert(items);
        return true;
    }


	@Override
	public ItemVo queryItemVoByItemNo(String itemNo) {
		QueryWrapper<Item> queryWrapper =new QueryWrapper<Item>();
    	queryWrapper.eq("item_no", itemNo);
    	Page<Item> page=new Page<Item>();
    	page.setCurrent(1);
    	page.setSize(1);
    	List<ItemVo> itemVoList=this.page(page,queryWrapper).getRecords();
    	if(itemVoList!=null&&itemVoList.size()>0) {
    		ItemVo itemVo=itemVoList.get(0);
    		ItemBrand itemBrand=itemBrandMapper.selectById(itemVo.getItemBrandId());
    		if(itemBrand!=null) {
    			itemVo.setItemBrandName(itemBrand.getIbrandName());
    		}
    		// 查询产品批次
    		List<ItemBatch> itemBatchList=itemBatchService.listItemBatchByItemNo(itemVo.getItemNo());
    		List<ItemBatchVo>voList=BeanUtil.copyPropsForList(itemBatchList, ItemBatchVo.class);
    		itemVo.setItemBatch(voList);
    		return itemVoList.get(0);
    	}
    	return null;
	}


	/**
	 * 查询团购商品详情
	 */
	@Override
	public PromotionItemDetailVo queryPromotionItemDetail(Integer userId,String itemNo, Integer promotionId) {
		 PromotionItemDetailVo  vo=new PromotionItemDetailVo();
		 ItemVo  itemVo= queryItemVoByItemNo(itemNo);
		 ItemBrand itemBrand=itemBrandMapper.selectById(itemVo.getItemBrandId());
 		 if(itemBrand!=null) {
 			itemVo.setItemBrandName(itemBrand.getIbrandName());
 		 }
		 itemVo.setPromotionId(promotionId);
		 itemVo.setPromotionTag(1);

		 //查询团购信息
		 PromotionInfoDto promotionInfoDto= promotionInfoMapper.queryPromotionInfo(promotionId);
		 //状态(0 待支付 1拼团中 2拼团成功 3已取消 4活动结束未成功)
		 vo.setStatus(changeStatus(promotionInfoDto.getStatus()));
		// groupCondition 成团条件（1:按参团人数;2:按成交数量）
		 if(promotionInfoDto.getGroupCondition()==1) {
			 vo.setSuccessDesc(promotionInfoDto.getMinSuccessNum()+"人成团");
		 }
		 else {
			 vo.setSuccessDesc(promotionInfoDto.getMinSuccessNum()+itemVo.getUnit()+"成团");
		 }
		 vo.setSurplusTime((promotionInfoDto.getEndTime().getTime()-System.currentTimeMillis())/1000);
		 List<PromotionItemUserVo> userList= promotionGroupItemService.listPromotionItemUser(promotionId);
		 vo.setItemVo(itemVo);
		 vo.setUserList(userList);
		 // 是否已收藏
    	 UserCollection userCollection=userCollectionService.queryUserCollectionByUserIdAndItemNo(userId, itemNo);
		 if(userCollection!=null) {
			vo.setCollectionTag(1);
		 }
		 List<ItemVo> recommendList=listItemRecommend(itemNo);
		 vo.setRecommendList(recommendList);
		 return vo;
	}


	private String changeStatus(byte s) {
	  //状态(0 待支付 1拼团中 2拼团成功 3已取消 4活动结束未成功)
		switch(s) {
		  case 0:
			  return "待支付";
		  case 1:
			  return "拼团中";
		  case 2:
			  return "拼团成功";
		  case 3:
			  return "已取消";
		  case 4:
			  return "活动结束未成功";
		}
		return "";
	}

	@Override
	public Item queryItemByItemNo(String itemNo) {
		QueryWrapper<Item> queryWrapper =new QueryWrapper<Item>();
    	queryWrapper.eq("item_no", itemNo);
    	List<Item> list=itemMapper.selectList(queryWrapper);
    	if(list.size()>0) {
    		return list.get(0);
    	}
		return null;
	}




}
