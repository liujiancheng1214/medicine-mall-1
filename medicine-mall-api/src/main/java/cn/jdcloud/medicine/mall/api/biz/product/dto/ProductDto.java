package cn.jdcloud.medicine.mall.api.biz.product.dto;


import cn.jdcloud.medicine.mall.domain.product.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDto{
    private Integer id;
    //产品名称
    private String name;
    //轮播图
    private String[] imgs;
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

    public Product wapperProduct(){
        Product p = new Product();
        p.setName(this.name);
        p.setIconImg(this.iconImg);
        p.setCategoryId1(this.categoryId1);
        p.setCategoryId2(this.categoryId2);
        p.setProvinceId(this.provinceId);
        p.setProvinceName(this.provinceName);
        p.setCityId(this.cityId);
        p.setCityName(this.cityName);
        p.setPartName(this.partName);
        p.setFirstPrice(this.firstPrice);
        p.setSecondPrice(this.secondPrice);
        p.setDetail(this.detail);
        p.setCreateTime(new Date());
        return p;
    }

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.name = product.getName();
        this.iconImg = product.getIconImg();
        this.categoryId1 = product.getCategoryId1();
        this.categoryId2 = product.getCategoryId2();
        this.provinceId = product.getProvinceId();
        this.provinceName = product.getProvinceName();
        this.cityId = product.getCityId();
        this.cityName = product.getCityName();
        this.partName = product.getPartName();
        this.firstPrice = product.getFirstPrice();
        this.secondPrice = product.getSecondPrice();
        this.detail = product.getDetail();
    }
}
