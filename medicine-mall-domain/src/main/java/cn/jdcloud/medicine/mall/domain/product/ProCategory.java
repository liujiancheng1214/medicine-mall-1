package cn.jdcloud.medicine.mall.domain.product;


import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_pro_category")
public class ProCategory extends BaseDomain {
    //自增id
    private Integer id;
    //分级 级别
    private Integer level;
    //父类id
    private Integer parentId;
    //类型名
    private String name;
    //排序
    private Integer sort;
    //是否启用  0 否  1是
    private Byte enable;

    /**
     * 是否删除 0否 1是
     */
    @TableField(exist = false)
    protected Byte isDeleted;


}
