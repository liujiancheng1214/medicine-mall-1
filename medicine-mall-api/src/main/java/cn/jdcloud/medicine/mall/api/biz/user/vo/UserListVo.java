package cn.jdcloud.medicine.mall.api.biz.user.vo;



import com.alibaba.fastjson.annotation.JSONField;
import cn.jdcloud.medicine.mall.domain.user.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserListVo {

        private String headImg;

        private String userName;

        private String mobile;

        private String nickName;

        private String realName;

        private String sexStr;

        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private Date lastLoginTime;

        public  UserListVo(){

        }

}
