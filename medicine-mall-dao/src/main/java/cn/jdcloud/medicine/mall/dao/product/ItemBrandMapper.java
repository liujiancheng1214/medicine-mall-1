package cn.jdcloud.medicine.mall.dao.product;

import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemBrandMapper extends BaseMapper<ItemBrand> {
    Map<String, Object> detail(@Param("id") Integer id);

    List<Map> subNode(Integer parentId);
}