package cn.jdcloud.medicine.mall.dao.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.product.ItemBrandDto;

public interface ItemMapper extends BaseMapper<Item> {
    int batchInsert(@Param("list") List<Item>list);
    
    List<ItemBrandDto> queryItemBrandInfo();
}