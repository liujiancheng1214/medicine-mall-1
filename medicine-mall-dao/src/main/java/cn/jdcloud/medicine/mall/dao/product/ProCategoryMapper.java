package cn.jdcloud.medicine.mall.dao.product;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.product.ProCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProCategoryMapper extends BaseMapper<ProCategory>{

    List<ProCategory> listByParentId(@Param("parentId") Integer parentId);

}