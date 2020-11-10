package cn.jdcloud.medicine.mall.dao.product;

import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemBatchMapper extends BaseMapper<ItemBatch> {
    void insertBatch(@Param("list") List<ItemBatch> itemBatchList);

}