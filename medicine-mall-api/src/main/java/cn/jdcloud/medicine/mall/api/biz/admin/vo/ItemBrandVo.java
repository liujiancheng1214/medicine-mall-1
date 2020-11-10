package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import cn.jdcloud.medicine.mall.domain.product.ItemBrand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
/**
 * @ClassName ItemDetailVo
 * @Author wuzhiyong
 * @Date 2020/8/13 14:23
 * @Version 1.0
 **/
@Data
@ApiModel("品牌对象")
public class ItemBrandVo  {
    //删除的
    public static final byte DELETE = 1;
    //未删除的
    public static final byte UNDELETE = 0;
    /**
     * 自增id
     */
    @ApiModelProperty(value="编码")
    private Integer id;

    /**
     * 父级id
     */
    @ApiModelProperty(value="")
    private Integer parentId;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value="品牌名称")
    private String ibrandName;

    /**
     * 品牌图片
     */
    @ApiModelProperty(value="品牌图片")
    private String brandImg;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 是否删除 0 未删除  1 删除
     */
    protected Byte isDeleted;
    
    public ItemBrandVo() {};

    public ItemBrandVo(ItemBrand itemBrand) {
        this.id = itemBrand.getId();
        this.parentId = itemBrand.getParentId();
        this.ibrandName = itemBrand.getIbrandName();
        this.brandImg = itemBrand.getBrandImg();
        this.sort = itemBrand.getSort();
        this.creator = itemBrand.getCreator();
        this.createTime = itemBrand.getCreateTime();
        this.updator = itemBrand.getUpdator();
        this.updateTime = itemBrand.getUpdateTime();
        this.isDeleted = itemBrand.getIsDeleted();
    }
}
