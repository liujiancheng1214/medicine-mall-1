package cn.jdcloud.medicine.mall.dao.product;

import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface ItemCategoryMapper extends BaseMapper<ItemCategory> {
    List<Map> subNode(Integer parentId);
}