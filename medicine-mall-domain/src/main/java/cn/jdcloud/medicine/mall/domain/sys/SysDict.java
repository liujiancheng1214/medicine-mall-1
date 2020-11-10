package cn.jdcloud.medicine.mall.domain.sys;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wuzhiyong
 */
@Data
@TableName(value = "t_sys_dict")
public class SysDict extends BaseDomain {
    public static final byte NUMBER_BYTE_ZERO = 0;
    public static final byte NUMBER_BYTE_ONE = 1;
    public static final byte NUMBER_BYTE_TWO = 2;

    /**退款原因*/
    public static final String REFUND_REASON = "REFUND_REASON";
    /**结算状态*/
    public static final String SETTLE_STATUS = "SETTLE_STATUS";

    /** 自增主键 */
    private Integer id;
    /** 针对一组字典的标识 */
    private String type;

    /** 显示的中文字符 */
    private String name;

    /** name 对应的 值 */
    private Integer value;

    /** 排序 */
    private Integer sort;

    /** 是否多选  0 无规则，1 多选  2 单选 */
    private Byte isMultiple;
    /** 备注 */
    private String remark;

}