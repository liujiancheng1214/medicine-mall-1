package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import cn.jdcloud.medicine.mall.domain.product.ItemCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
/**
 * @ClassName ItemCategoryVo
 * @Author wuzhiyong
 * @Date 2020/8/14 12:03
 * @Version 1.0
 **/
@Data
public class ItemCategoryVo {
    //删除的
    public static final byte DELETE = 1;
    //未删除的
    public static final byte UNDELETE = 0;
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 父类id
     */
    private Integer parentId;

    /**
     * 商品分类编码
     */
    private String categoryNo;

    /**
     * 商品分类名字
     */
    private String categoryName;

    /**
     * 商品分类图标
     */
    private String categoryImg;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private Integer updator;

    /**
     * 是否删除 0 未删除  1 删除
     */
    protected Byte isDeleted;

    public ItemCategoryVo(ItemCategory category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.categoryNo = category.getCategoryNo();
        this.categoryName = category.getCategoryName();
        this.categoryImg = category.getCategoryImg();
        this.sort = category.getSort();
        this.createTime = category.getCreateTime();
        this.creator = category.getCreator();
        this.updateTime = category.getUpdateTime();
        this.updator = category.getUpdator();
        this.isDeleted = category.getIsDeleted();
    }
}
