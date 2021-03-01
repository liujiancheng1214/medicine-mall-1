package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandDetailVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemBrandVo;
import cn.jdcloud.medicine.mall.api.biz.admin.vo.ItemSubBrandVo;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBrandService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.CategoryTreeVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.dao.product.ItemBrandMapper;
import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @ClassName ItemBrandServiceImpl
 * @Author wuzhiyong
 * @Date 2020/8/13 9:21
 * @Version 1.0
 **/
@Service
public class ItemBrandServiceImpl extends ServiceImpl<ItemBrandMapper, ItemBrand> implements ItemBrandService {
    @Resource
    ItemBrandMapper itemBrandMapper;
    //逻辑删除
    @Override
    public void delete(ItemBrand itemBrand) {
        itemBrand.setIsDeleted(ItemBrand.DELETE);
        this.updateById(itemBrand);
    }

    @Override
    public ItemBrandDetailVo detail(Integer id) {
        //查询父品牌信息
        ItemBrandDetailVo itemBrandDetailVo = null;
        Map<String,Object> result = itemBrandMapper.detail(id);
        if (result!=null){
            itemBrandDetailVo = new ItemBrandDetailVo(result);
            //查询子品牌
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("parent_id", itemBrandDetailVo.getId());
            itemBrandDetailVo.setSubItem(this.list(wrapper));
        }
        return itemBrandDetailVo;
    }

    @Override
    public void undelete(ItemBrand itemBrand) {
        itemBrand.setIsDeleted(ItemBrand.UNDELETE);
        this.updateById(itemBrand);
    }
    @Override
    public List<ItemSubBrandVo> subNode(Integer parentId) {
        List<Map> categoryVos =  itemBrandMapper.subNode(parentId);
        List<ItemSubBrandVo> result = new ArrayList<>();
        if (categoryVos!=null && categoryVos.size()>0){
            for (Map m : categoryVos){
                result.add(new ItemSubBrandVo(m));
            }
        }
        return result;
    }

    @Override
    public Page pageList(Page page, String searchValue) {
        QueryWrapper queryWrapper = new QueryWrapper<ItemBrand>();
        queryWrapper.like(StringUtils.isNotBlank(searchValue),"ibrand_name",searchValue);
        queryWrapper.orderByAsc("parent_id","sort");
        page =  this.page(page,queryWrapper);
        List<ItemBrandVo> brandList = new ArrayList<ItemBrandVo>();
        if (page.getOrders()!=null && page.getRecords().size()>0){
            for (Object o :page.getRecords()){
                brandList.add(new ItemBrandVo((ItemBrand)o));
            }
        }
        page.setRecords(brandList);
        return page;
    }

    @Override
    public void saveBrand(ItemBrand itemBrand, HttpServletRequest request) {
        UserSession session = (UserSession) request.getSession().getAttribute(UserSession.NAME);
        itemBrand.setCreator(session.getUserId());
        itemBrand.setCreateTime(new Date());
        itemBrand.setUpdateTime(new Date());
        itemBrand.setUpdator(session.getUserId());
        itemBrand.setIsDeleted(ItemBrand.UNDELETE);
        this.save(itemBrand);
    }

	@Override
	public Page<ItemBrandVo> pageBrandList(int pageNum,int pageSize, String searchValue) {
		Page<ItemBrand> itemBrandPage=new Page<ItemBrand>();
		itemBrandPage.setCurrent(pageNum);
		itemBrandPage.setSize(pageSize);
		QueryWrapper<ItemBrand> wrapper = new QueryWrapper<>();
		if(StringUtils.isNotBlank(searchValue)) {
			wrapper.like("ibrand_name", searchValue);
		}
	     wrapper.orderByAsc("sort");
		 itemBrandPage=this.page(itemBrandPage, wrapper);
		 List<ItemBrandVo> voList=new ArrayList<>();
		 Page<ItemBrandVo>  itemBrandVo=new Page<ItemBrandVo>();
		 voList= itemBrandPage.getRecords().stream().map(itemBrand -> new ItemBrandVo(itemBrand)).collect(Collectors.toList());
		 itemBrandVo.setRecords(voList);
		 return itemBrandVo;
	}

	@Override
	public List<ItemBrandVo> listHotBandList() {
		 QueryWrapper<ItemBrand> wrapper = new QueryWrapper<>();
		 wrapper.eq("hot_tag", 1).eq("is_deleted", 0).orderByAsc("sort");
		 List<ItemBrand>  list=this.list(wrapper);
		 List<ItemBrandVo> voList= BeanUtil.copyPropsForList(list, ItemBrandVo.class);
		 return voList;
	}
}
