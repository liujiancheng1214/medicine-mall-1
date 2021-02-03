package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import cn.jdcloud.medicine.mall.api.biz.config.ConfigService;
import cn.jdcloud.medicine.mall.api.biz.coupon.service.CouponService;
import cn.jdcloud.medicine.mall.api.biz.product.dto.ItemDto;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ItemBusinessEnum;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ItemImportantEnum;
import cn.jdcloud.medicine.mall.api.biz.product.enums.ItemStopBuyEnum;
import cn.jdcloud.medicine.mall.api.biz.product.excel.ItemExcel;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CouponVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.FilterBrandVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.FilterCategoryVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.FilterVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.IndexCouponItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.IndexPromotionItemVo;
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
import cn.jdcloud.medicine.mall.api.constant.Constant;
import cn.jdcloud.medicine.mall.dao.product.ItemBrandMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemCategoryMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemRecommendMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionGroupItemMapper;
import cn.jdcloud.medicine.mall.dao.promotion.PromotionInfoMapper;
import cn.jdcloud.medicine.mall.domain.admin.Admin;
import cn.jdcloud.medicine.mall.domain.config.Config;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import cn.jdcloud.medicine.mall.domain.product.ItemBrandDto;
import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import cn.jdcloud.medicine.mall.domain.product.ItemRecommend;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionInfoDto;
import cn.jdcloud.medicine.mall.domain.promotion.PromotionItem;
import cn.jdcloud.medicine.mall.domain.user.UserCollection;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    @Resource
    private ItemMapper itemMapper;
    @Resource
    private ItemCategoryMapper itemCategoryMapper;
    @Resource
    private ItemBrandMapper itemBrandMapper;
    @Resource
    private ItemBatchService itemBatchService;
    @Resource
    private CouponService couponService;
    @Resource
    private AdminService adminService;
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
    @Resource
    private ConfigService configService;
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
        // 设置SKU 
        for (ItemBatch batch : itemBatch) {
            batch.setItemNo(itemNo);
            batch.setIsDel((byte)0);
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
        	ItemBatch  itemBatch2=new ItemBatch();
        	itemBatch2.setIsDel((byte)1);
        	QueryWrapper<ItemBatch> queryWrapper= new QueryWrapper<ItemBatch>().eq("item_no", itemNo).eq("is_del", 0);
        	List<ItemBatch>  batchList=itemBatchService.list(queryWrapper);
        	//  如果批次号没变 则需要用数据库之前的SKU把之前的
        	for(ItemBatch ib:itemBatch) {
        		for(ItemBatch dbIb:batchList) {
        			if(ib.getBatchNo().equals(dbIb.getBatchNo())) {
        				ib.setSku(dbIb.getSku());
        				break;
        			}
        		}
        		if(ib.getSku()==null||"".equals(ib.getSku())) {
        			ib.setSku(ib.generateSKU());
        		}
        	}
        	// 把之前的删除
        	itemBatchService.update(itemBatch2,queryWrapper );
        	// 新增
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
		 //t_promotion_group_item
		 //查询团购信息
		 PromotionInfoDto promotionInfoDto= promotionInfoMapper.queryPromotionInfo(promotionId);
		
		 //团购价格
		 PromotionItem  promotionItem=promotionGroupItemService.promotionItemOne(promotionId, itemNo);
		 itemVo.setPromotionPrice(promotionItem.getPromotionPrice());
		 
		 //状态(0 待支付 1拼团中 2拼团成功 3已取消 4活动结束未成功)
		 vo.setStatus(changeStatus(promotionInfoDto.getStatus()));
		// groupCondition 成团条件（1:按参团人数;2:按成交数量）
		 if(promotionInfoDto.getGroupCondition()==1) {
			 vo.setSuccessDesc("人成团");
			 vo.setSuccessNum(promotionInfoDto.getMinSuccessNum());
		 }
		 else {
			 vo.setSuccessNum(promotionInfoDto.getMinSuccessNum());
			 vo.setSuccessDesc(itemVo.getUnit()+"成团");
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


	@Override
	public IndexPromotionItemVo queryIndexPromotionItemDetail(Integer userId) {
		IndexPromotionItemVo indexPromotionItemVo=new IndexPromotionItemVo();
		Config config=configService.queryByCode(Constant.INDEX_ITEM_PROMOTION);
		String value=config.getValue();
		String itemNo=value.split("-")[0];
		Integer promotionId=Integer.parseInt(value.split("-")[1]);
		PromotionInfoDto promotionInfoDto= promotionInfoMapper.queryPromotionInfo(promotionId);
		PromotionItem  promotionItem=promotionGroupItemService.promotionItemOne(promotionId, itemNo);
		Item item= queryItemByItemNo( itemNo);
		indexPromotionItemVo.setEffectiveDate(item.getEffectiveDate());
		indexPromotionItemVo.setImgCover(item.getImgCover());
		indexPromotionItemVo.setItemName(item.getItemName());
		indexPromotionItemVo.setItemNo(item.getItemNo());
		indexPromotionItemVo.setLimitNum(promotionItem.getItemNum());
		indexPromotionItemVo.setPromotionId(promotionId);
		indexPromotionItemVo.setPromotionPrice(promotionItem.getPromotionPrice());
		indexPromotionItemVo.setSoldNum(promotionInfoDto.getItemNum());
		indexPromotionItemVo.setSurplusTime((promotionInfoDto.getEndTime().getTime()-System.currentTimeMillis())/1000);
		return indexPromotionItemVo;
	}


	
	/**
	 * 查询首页优惠券商品信息
	 */
	@Override
	public List<IndexCouponItemVo> queryIndexCouponItems(Integer userId,int pageNum,int pageSize) {
		/**
		 *  1、首先查询所有未过期的优惠券
		 *  
		 *  2、解析所有优惠券对应的商品编码 类别编码 品牌编码
		 *  
		 *  3、再将第二步的解析作为参数去作为查询条件查询商品表
		 *  
		 *  4、最后将查询出来的商品和优惠券进行对应 计算商品的折扣金额
		 *  
		 */
		Page<CouponVo> page=couponService.listCoupon(1, Integer.MAX_VALUE, null, null);
		List<CouponVo> couponList=page.getRecords();
		
		List<String> itemIds=new ArrayList<>();
		List<String> categoryIds=new ArrayList<>();
		List<String> brandIds=new ArrayList<>();
		
		for(CouponVo  vo:couponList) {
			//limitType; //限制类型:    1 商品限制  2 品牌限制 3 类别限制
			Byte limitType=vo.getLimitType();
			if(limitType==1) {
				itemIds.addAll(Arrays.asList(vo.getLimitItenIds().split(",")));
			}
			else if(limitType==2) {
				brandIds.addAll(Arrays.asList(vo.getLimitItenIds().split(",")));
			}
			else if(limitType==3) {
				categoryIds.addAll(Arrays.asList(vo.getLimitItenIds().split(",")));
			}
		}
		Page<Item> itemPage=new Page<>();
		itemPage.setSize(pageSize);
		itemPage.setCurrent(pageNum);
		QueryWrapper<Item> queryWrapper =new QueryWrapper<Item>();
		if(itemIds.size()>0) {
			queryWrapper.or().in("id", itemIds);
		}
		if(categoryIds.size()>0) {
			queryWrapper.or().in("item_category_id", itemIds);
		}
		if(brandIds.size()>0) {
			queryWrapper.or().in("item_brand_id", itemIds);
		}
		itemPage=itemMapper.selectPage(itemPage, queryWrapper);
		
		List<Item> items=itemPage.getRecords();
		List<IndexCouponItemVo> result=new ArrayList<>();
		for(Item item:items) {
			IndexCouponItemVo  indexCouponItemVo=new IndexCouponItemVo();
			BeanUtil.copyProperties(item, indexCouponItemVo);
			BigDecimal discountPrice=matchingDiscountAmount(couponList,item);
			indexCouponItemVo.setDiscountPrice(item.getPlatformPrice().subtract(discountPrice));
			result.add(indexCouponItemVo);
		}
		return result;
	}


	/**
	 * 匹配折扣金额
	 * @return
	 */
	private BigDecimal matchingDiscountAmount(List<CouponVo> couponList,Item item) {
		//limitType; //限制类型:    1 商品限制  2 品牌限制 3 类别限制
		BigDecimal finallyPrice=BigDecimal.ZERO;
		BigDecimal itemPrice=BigDecimal.ZERO;
		BigDecimal categoryPrice=BigDecimal.ZERO;
		BigDecimal brandPrice=BigDecimal.ZERO;
		// 计算某个商品的最大折扣金额
		for(CouponVo vo:couponList) {
			List<String> list=new ArrayList<>();
			list=Arrays.asList(vo.getLimitItenIds().split(","));
			if(vo.getLimitType()==1&&list.contains(item.getId()+"")
					&&itemPrice.compareTo(vo.getDiscountAmount())==-1) {
				itemPrice=vo.getDiscountAmount();
			}
			else if(vo.getLimitType()==2&&list.contains(item.getItemBrandId()+"")
					&&brandPrice.compareTo(vo.getDiscountAmount())==-1) {
				brandPrice=vo.getDiscountAmount();
			}
			else if(vo.getLimitType()==3&&list.contains(item.getItemCategoryId()+"")
					&&categoryPrice.compareTo(vo.getDiscountAmount())==-1) {
				categoryPrice=vo.getDiscountAmount();
			}
		}
		
		if(itemPrice.compareTo(brandPrice)==-1) {
			finallyPrice=brandPrice;
		}
		else {
			finallyPrice=itemPrice;
		}
		
		if(finallyPrice.compareTo(categoryPrice)==-1) {
			finallyPrice=categoryPrice;
		}
		return finallyPrice;
	}


	@Override
	public List<ItemVo> listItemsByItemName(String itemName) {
		List<Item> list=itemMapper.selectList(new QueryWrapper<Item>().like("item_name", itemName));
		List<ItemVo> voList=new ArrayList<>();
		for(Item item:list) {
			ItemVo  itemVo=new ItemVo();
			itemVo.setId(item.getId());
			itemVo.setItemNo(item.getItemNo());
			itemVo.setItemName(item.getItemName());
			voList.add(itemVo);
		}
		return voList;
	}


	@Override
	public FilterVo queryItemFilterInfo() {
		//查询商品的所有类别
		 FilterVo  filterVo=new FilterVo();
		 List<FilterCategoryVo> categoryList=new ArrayList<>();
		 List<FilterBrandVo> brandList=new ArrayList<>();;
		//查询商品的品牌
		List<ItemBrandDto> list=itemMapper.queryItemBrandInfo();
		brandList=BeanUtil.copyPropsForList(list, FilterBrandVo.class);
		
		QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("DISTINCT  item_category_id id");
		List<Map<String, Object>> maps = itemMapper.selectMaps(queryWrapper);
		List<Integer>categoryIds=new ArrayList<>();
		for(Map<String, Object> map:maps) {
			categoryIds.add((Integer)map.get("id"));
		}
		List<ItemCategory> categorys=itemCategoryMapper.selectBatchIds(categoryIds);
		
		categoryList=BeanUtil.copyPropsForList(categorys, FilterCategoryVo.class);
		filterVo.setBrandList(brandList);
		filterVo.setCategoryList(categoryList);
		return filterVo;
	}


}
