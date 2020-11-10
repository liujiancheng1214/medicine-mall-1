package cn.jdcloud.medicine.mall.api.biz.product.service;

import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandDetailVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemSubBrandVo;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
public interface ItemBrandService extends IService<ItemBrand> {
	
	/**
	 * 客户端分页查询品牌列表 
	 * @param page
	 * @param searchValue
	 * @return
	 */
	Page<ItemBrandVo> pageBrandList(int pageNum,int pageSize,String searchValue);
	
	/**
	 * 查询热销品牌
	 * @return
	 */
	List<ItemBrandVo> listHotBandList();
	
    //逻辑删除
    void delete(ItemBrand itemBrand);

    ItemBrandDetailVo detail(Integer id);
    //撤销删除
    void undelete(ItemBrand itemBrand);

    List<ItemSubBrandVo> subNode(Integer parentId);

    Page pageList(Page page, String searchValue);

    void saveBrand(ItemBrand itemBrand, HttpServletRequest request);
}
