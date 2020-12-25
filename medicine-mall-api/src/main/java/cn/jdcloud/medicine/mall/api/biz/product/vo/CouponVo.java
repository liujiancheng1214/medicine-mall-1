package cn.jdcloud.medicine.mall.api.biz.product.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("优惠券对象")
public class CouponVo {

	private Integer id;

	@ApiModelProperty("优惠券标题")
    private String title; //优惠券标题
	@ApiModelProperty("图片")
    private String image;  //图片

    private Integer budgetUseTotalQty; //使用总量

    private Integer budgetSendTotalQty; //发放总量
    @ApiModelProperty("1:代金券 2.折扣券 3.满减券")
    private Byte type; //1:代金券 2.折扣券 3.满减券
    @ApiModelProperty("限制类型:  1 商品限制  2 品牌限制 3 类别限制")
    private Byte limitType; //限制类型: 0 不受限制   1 商品限制  2 品牌限制 3 类别限制 
    
    private String limitItenIds; //限制商品
    
    private Integer limitItemNum; //只针对limitType=1 限制商品数量 默认1 当为2时,表示最少购买2件该商品才可使用

    private BigDecimal discountAmount; //优惠金额

    private BigDecimal discountLimitAmount; //满减优惠金额
    @ApiModelProperty("优惠券描述")
    private String description; //优惠券描述

    private String limitUserLevelIds; //限制的顾客等级id

    private Byte isHandsel; //是否可以转增 0否  1是

    private Integer limitNum; //用户领取数量限制0:不限制（默认值）n:限领n张

    private Byte isForbidOverlayPreferential; //是否禁止优惠券叠加使用 0不能 1允许

    private Integer expireNoticeDays; //过期前几天提示

    private Integer expireDays; //优惠券有效时间单位(天)
    @ApiModelProperty("优惠券有效截止日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date expireTime; //优惠券有效时间
    @ApiModelProperty("优惠券有效开始日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;

    private Integer creator;

    private Integer updator;
}
