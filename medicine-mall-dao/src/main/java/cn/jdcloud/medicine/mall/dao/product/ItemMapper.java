package cn.jdcloud.medicine.mall.dao.product;

import cn.jdcloud.medicine.mall.domain.product.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {
    int batchInsert(@Param("list") List<Item>list);
}