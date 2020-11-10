package cn.jdcloud.medicine.mall.api.biz.product.vo;

import cn.jdcloud.medicine.mall.domain.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品VO
 * @author HuZhengYu
 * @date 20:31 2020/7/8
 */
@Data
@ApiModel("商品对象")
public class ProductVo {

	/** 用户id*/
	@ApiModelProperty(value="用户id")
	private Integer userId;
	/** 自增id*/
	private Integer id;
	/** 商品名称*/
	@ApiModelProperty(value="商品名称")
	private String name;
	/** 展示图*/
	@ApiModelProperty(value="展示图")
	private String iconImg;
	/** 轮播图*/
	@ApiModelProperty(value="轮播图")
	private List url;
	/** 商品一级分类*/
	@ApiModelProperty(value="商品一级分类ID")
	private Integer categoryId1;
	/** 商品一级分类*/
	@ApiModelProperty(value="商品一级分类")
	private String category1;
	/** 商品二级分类*/
	@ApiModelProperty(value="商品二级分类ID")
	private Integer categoryId2;
	/** 商品二级分类*/
	@ApiModelProperty(value="商品二级分类")
	private String category2;
	/** 产地省名称*/
	@ApiModelProperty(value="")
	private String provinceName;
	/** 产地市名称*/
	@ApiModelProperty(value="产地市名称")
	private String cityName;
	/** 每份名称*/
	@ApiModelProperty(value="每份名称")
	private String partName;
	/** 第一价格*/
	@ApiModelProperty(value="第一价格")
	private BigDecimal firstPrice;
	/** 第二价格*/
	@ApiModelProperty(value="第二价格")
	private BigDecimal secondPrice;
	/** 商品详情*/
	@ApiModelProperty(value="商品详情")
	private String detail;
	/** 已售数量*/
	@ApiModelProperty(value="已售数量")
	private Integer saleNum;
	/** 1上架 0未上架*/
	@ApiModelProperty(value=" 1上架 0未上架")
	private Byte inSale;
	
	public  ProductVo(Product pro){
		this.userId = pro.getUserId();
		this.id = pro.getId();
		this.name = pro.getName();
		this.iconImg = pro.getIconImg();
		this.provinceName = pro.getProvinceName();
		this.cityName = pro.getCityName();
		this.partName = pro.getPartName();
		this.firstPrice = pro.getFirstPrice();
		this.secondPrice = pro.getSecondPrice();
		this.detail = pro.getDetail();
		this.saleNum = pro.getSaleNum();
		this.inSale = pro.getInSale();
		this.categoryId1 = pro.getCategoryId1();
		this.categoryId2 = pro.getCategoryId2();
	}
}
