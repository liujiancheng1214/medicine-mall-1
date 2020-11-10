package cn.jdcloud.medicine.mall.api.biz.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemSubCategoryVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryTreeVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryVo;
import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
public interface ItemCategoryService extends IService<ItemCategory> {
	
	List<CategoryVo> listCategory();
	
    List<ItemSubCategoryVo> subNode(Integer parentId);

    Page pageList(Page page, String searchValue);

    void add(ItemCategory itemCategory, HttpServletRequest request);

    /**
     * 获取商品类目完整树结构
     * @return
     */
    List<CategoryTreeVo> getTree();
}
