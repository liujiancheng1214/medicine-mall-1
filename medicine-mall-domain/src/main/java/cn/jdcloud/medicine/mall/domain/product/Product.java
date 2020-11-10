package cn.jdcloud.medicine.mall.domain.product;


import com.baomidou.mybatisplus.annotation.TableName;
import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_product")

public class Product extends BaseDomain {
    //果农商品
    public static final byte PRO_GROWER = 1;
    //店铺商品
    public static final byte PRO_SELLER = 2;
    //上架
    public static final byte IN_SELL = 1;
    //下架
    public static final byte OFF_SELL = 0;
    //自增id
    private Integer id;
    //商品类型  1 果农发布   2 店铺发布
    private Byte type;
    //发布人id
    private Integer userId;
    //产品名称
    private String name;
    //展示图
    private String iconImg;
    //产品类型id
    private Integer categoryId1;
    private Integer categoryId2;
    //产地省id
    private Integer provinceId;
    //产地省名称
    private String provinceName;
    //产地市id
    private Integer cityId;
    //产地市名称
    private String cityName;
    //每份名称
    private String partName;
    //第一价格
    private BigDecimal firstPrice;
    //第二价格
    private BigDecimal secondPrice;
    //商品详情
    private String detail;
    //已售数量
    private Integer saleNum;
    //1上架 0未上架
    private Byte inSale;

}