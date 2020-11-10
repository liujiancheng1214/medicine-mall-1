package cn.jdcloud.medicine.mall.dao.promotion;

import cn.jdcloud.medicine.mall.domain.promotion.GroupInfo;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoDto;
import cn.jdcloud.medicine.mall.domain.promotion.GroupInfoResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenQF
 * @desc 拼团记录数据访问层
 * @date 2020/8/24 0024 13:55
 */
public interface GroupInfoMapper extends BaseMapper<GroupInfo> {

    List<GroupInfoResult> listGroupInfo(@Param("page") Page<GroupInfoResult> page, @Param("groupInfoDto") GroupInfoDto groupInfoDto);

    Integer listCount(@Param("groupInfoDto")GroupInfoDto groupInfoDto);
}
