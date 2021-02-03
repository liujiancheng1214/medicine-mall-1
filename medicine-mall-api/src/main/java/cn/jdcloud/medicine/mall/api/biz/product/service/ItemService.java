package cn.jdcloud.medicine.mall.api.biz.product.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.product.dto.ItemDto;
import cn.jdcloud.medicine.mall.api.biz.product.excel.ItemExcel;
import cn.jdcloud.medicine.mall.api.biz.product.vo.FilterVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.IndexCouponItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.IndexPromotionItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemDetailVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PromotionItemDetailVo;
import cn.jdcloud.medicine.mall.domain.product.Item;


public interface ItemService extends IService<Item> {

	
	Item  queryItemByItemNo(String itemNo);

	List<ItemVo> listItemRecommend(String itemNo);

	ItemVo queryItemVoByItemNo(String itemNo);
	/**
     * 商品列表分页
     * @param page
     * @param queryWrapper
     * @return
     */
	 Page<ItemVo> page(Page<Item> page, QueryWrapper<Item> queryWrapper);

	/**
     * web商品列表分页
     * @param page
     * @param queryWrapper
     * @return
     */
	 Page<ItemVo> webPage(Page<Item> page, QueryWrapper<Item> queryWrapper);

    /**
     * 获取导出数据集合
     * @param queryWrapper
     * @return
     */
    List<ItemExcel> exportList (QueryWrapper<Item> queryWrapper);

    /**
     * 保存商品信息数据集合
     * @param dto
     * @param userId
     * @return
     */
    boolean save (ItemDto dto,Integer userId);

    /**
     * 获取导出模板下拉选项
     * @return
     */
    Map<String,String[]> getGuideOptions();

    Map<String, Map> getItemDict(boolean reverse) ;

    boolean importItems(List<ItemExcel> list,Integer userId);

    /**
     * 商品详情
     * @param itemNo
     * @return
     */
	ItemDetailVo queryItemDetail(Integer userId,String itemNo);

	/**
	 * 查询团购商品详情
	 * @param itemNo
	 * @param promotionId
	 * @return
	 */
	PromotionItemDetailVo queryPromotionItemDetail(Integer userId,String itemNo,Integer promotionId);

    
	/**
	 * 查询首页团购信息
	 * @param userId
	 * @return
	 */
	IndexPromotionItemVo queryIndexPromotionItemDetail(Integer userId);
	
	/**
	 * 查询首页优惠商品信息
	 * @param userId
	 * @return
	 */
	List<IndexCouponItemVo> queryIndexCouponItems(Integer userId,int pageNum,int PageSize);
	
	/**
	 * 根据商品名称模糊查询
	 * @param itemName
	 * @return
	 */
	List<ItemVo> listItemsByItemName(String itemName);
	
	/**
	 * 查询商品筛选信息
	 * @return
	 */
	FilterVo  queryItemFilterInfo();
	
}
