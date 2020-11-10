package cn.jdcloud.medicine.mall.client.user;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: caojie
 * @Date: 2018-12-05 16:49
 * @Description:
 */
@Data
public class UserSession implements Serializable {

    public static final String NAME = "userSession";

    private static final long serialVersionUID = -7750285773827566679L;

    private Integer userId;

    private String account;

    private String userName;

    private String headImg;//用户头像

    //用户权限因子
    Set<String> permissionValue = new HashSet<String>();
    //用户角色id集合
    Set<Integer> roleValue = new HashSet<Integer>();

}
