package cn.jdcloud.medicine.mall.domain.product;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

import org.springframework.beans.BeanUtils;

/**
* Created by Mybatis Generator 2020/08/12
*/
@Data
@TableName(value = "t_item_brand")
public class ItemBrand {
    //删除的
    public static final byte DELETE = 1;
    //未删除的
    public static final byte UNDELETE = 0;
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 品牌名称
     */
    private String ibrandName;

    /**
     * 品牌图片
     */
    private String brandImg;

    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 是否热销
     */
    private Byte hotTag;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updator;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0 未删除  1 删除
     */
    protected Byte isDeleted;


}
