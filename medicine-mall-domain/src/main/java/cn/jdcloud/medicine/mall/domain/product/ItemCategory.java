package cn.jdcloud.medicine.mall.domain.product;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
* Created by Mybatis Generator 2020/08/12
*/
@Data
@TableName(value = "t_item_category")
public class ItemCategory {

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
    private Date createTime;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Integer updator;

    /**
     * 是否删除 0 未删除  1 删除
     */
    protected Byte isDeleted;


}