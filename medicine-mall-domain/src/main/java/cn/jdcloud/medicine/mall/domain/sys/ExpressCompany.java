package cn.jdcloud.medicine.mall.domain.sys;

import cn.jdcloud.framework.core.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 快递公司实体类
 *
 * @author HuZhengYu
 * @date 15:07 2020/8/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "t_express_company")
public class ExpressCompany extends BaseDomain {

    /** 自增id**/
    private Integer id;
    /** 快递公司名称**/
    private String name;
    /** 快递公司编码**/
    private String code;

}
