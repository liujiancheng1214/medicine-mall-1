package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemCategoryVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemSubCategoryVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemCategoryService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryTreeVo;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.dao.product.ItemCategoryMapper;
import cn.jdcloud.medicine.mall.domain.product.ItemCategory;

/**
 * @ClassName ItemCategoryServiceImpl
 * @Author wuzhiyong
 * @Date 2020/8/13 9:19
 * @Version 1.0
 **/
@Service
public class ItemCategoryServiceImpl extends ServiceImpl<ItemCategoryMapper, ItemCategory> implements ItemCategoryService {
    @Resource
    ItemCategoryMapper itemCategoryMapper;
    @Override
    public List<ItemSubCategoryVo> subNode(Integer parentId) {
        List<Map> categoryVos =  itemCategoryMapper.subNode(parentId);
        List<ItemSubCategoryVo> result = new ArrayList<>();
        if (categoryVos!=null && categoryVos.size()>0){
            for (Map m : categoryVos){
                result.add(new ItemSubCategoryVo(m));
            }
        }
        return result;
    }

    @Override
    public Page pageList(Page page, String searchValue) {
        QueryWrapper queryWrapper = new QueryWrapper<ItemCategory>();
        queryWrapper.like(StringUtils.isNotBlank(searchValue),"category_name",searchValue);
        queryWrapper.or();
        queryWrapper.like(StringUtils.isNotBlank(searchValue),"category_no",searchValue);
        queryWrapper.orderByAsc("parent_id","sort");
        page =  this.page(page,queryWrapper);
        List<ItemCategoryVo> categoryList = new ArrayList<ItemCategoryVo>();
        if (page.getOrders()!=null && page.getRecords().size()>0){
            for (Object o :page.getRecords()){
                categoryList.add(new ItemCategoryVo((ItemCategory)o));
            }
        }
        page.setRecords(categoryList);
        return page;
    }

    @Override
    public void add(ItemCategory itemCategory, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemCategory.setCreator(session.getUserId());
        itemCategory.setUpdator(session.getUserId());
        itemCategory.setCreateTime(new Date());
        itemCategory.setUpdateTime(new Date());
        itemCategory.setIsDeleted(ItemCategory.UNDELETE);
        this.save(itemCategory);
    }


    @Override
    public List<CategoryTreeVo> getTree() {
        List<ItemCategory> categorys = itemCategoryMapper.selectList(new QueryWrapper<ItemCategory>().eq("is_deleted",0));
        List<CategoryTreeVo> vo = categorys.stream().map(category -> new CategoryTreeVo(category)).collect(Collectors.toList());
        Map<Integer, List<CategoryTreeVo>> permMap = vo.stream().collect(Collectors.groupingBy(CategoryTreeVo::getParentId, Collectors.toList()));
        List<CategoryTreeVo> vos = permMap.get(0);
        if (vos==null){
            vos = new ArrayList<>();
        }
        this.initChildren(vos,permMap);
        return vos;
    }

    private void initChildren(List<CategoryTreeVo> vos, Map<Integer, List<CategoryTreeVo>> map){
        vos.forEach(vo -> {
            List<CategoryTreeVo> childrens = map.get(vo.getValue());
            if(childrens!=null){
                vo.setChildren(childrens);
                initChildren(childrens,map);
            }
        });
    }

	@Override
	public List<CategoryVo> listCategory() {
		List<CategoryVo> list=new ArrayList<>();
		List<ItemCategory> parentList=itemCategoryMapper.selectList(new QueryWrapper<ItemCategory>().eq("is_deleted", 0).eq("parent_id", 0));
		
		List<ItemCategory>  allList=itemCategoryMapper.selectList(new QueryWrapper<ItemCategory>().eq("is_deleted", 0));
		
		for(ItemCategory itemCategory:parentList) {
			CategoryVo categoryVo=new CategoryVo();
			BeanUtil.copyProperties(itemCategory, categoryVo);
			categoryVo.setParentId(0);
			List<ItemCategory> subList=allList.stream().filter(bean->bean.getParentId()==itemCategory.getId()).collect(Collectors.toList());
			List<CategoryVo> subVoList=new ArrayList<>();
			for(ItemCategory sub:subList) {
				CategoryVo subVo=new CategoryVo();
				BeanUtil.copyProperties(sub, subVo);
				subVo.setParentId(itemCategory.getId());
				subVoList.add(subVo);
			}
			categoryVo.setChildren(subVoList);
			list.add(categoryVo);
		}
		
		
		return list;
	}
}
