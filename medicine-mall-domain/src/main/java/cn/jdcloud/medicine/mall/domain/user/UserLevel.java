package cn.jdcloud.medicine.mall.domain.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author chenQF
 * @date 2020/8/14 0014 16:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_user_level")
public class UserLevel {

    private Integer id;

    private String levelName;

    private Integer levelValue;

    private String icon;

}
