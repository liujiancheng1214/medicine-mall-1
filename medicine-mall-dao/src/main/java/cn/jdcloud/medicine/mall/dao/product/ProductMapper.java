package cn.jdcloud.medicine.mall.dao.product;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product>{

    List<Product> selectByName(@Param("userId") Integer userId, @Param("name") String name);
    //删除商品
    int delectById(Integer id);

    //上架商品
    int onTheShelf(Integer id);

    //下架商品
    int offTheShelf(Integer id);

    //编辑商品
    int edit(Integer id);

    //首页根据名称搜索
    List<Product> selProductByNameList(String name);

    //首页根据产地、销量、分类、价格获取集合
    List<Product> listBySort(@Param("varieties") Integer varieties,
                             @Param("provinceId") Integer provinceId,
                             @Param("salesVolume") String salesVolume,
                             @Param("price") String price);


    List<Product> listByTerm(@Param("userId") Integer userId,
                             @Param("varieties") String varieties,
                             @Param("salesVolume") String salesVolume,
                             @Param("price") String price);
}
