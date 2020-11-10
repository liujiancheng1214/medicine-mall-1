package cn.jdcloud.medicine.mall.domain.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_sys_cons")
public class SysCons extends BaseDomain {

    private static final long serialVersionUID = 4633747475970585969L;

    private String idKey;
    private ConsEnum module;
    private String name;
    private String value;
    public SysCons(){
    }

    public SysCons(String idKey){
        this.idKey = idKey;
    }

}