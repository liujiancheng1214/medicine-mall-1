package cn.jdcloud.medicine.mall.api.biz.user.service;

import cn.jdcloud.medicine.mall.domain.user.UserBankCard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chenQF
 * @desc 用户银行卡接口层
 * @date 2020/8/26 0026 9:44
 */
public interface UserBankCardService extends IService<UserBankCard> {

    List<UserBankCard> getByUserId(Integer userId);

}
