package cn.jdcloud.medicine.mall.api.biz.user.service;

import cn.jdcloud.medicine.mall.domain.user.UserImg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @desc 用户证件照片业务接口
 */
public interface UserImgService extends IService<UserImg>{

    /**
     * 查询客户证件图片
     * @param userId
     * @return
     */
    List<UserImg> getAllUserImg(Integer userId);

}
