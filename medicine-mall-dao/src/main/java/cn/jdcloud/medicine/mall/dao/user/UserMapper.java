package cn.jdcloud.medicine.mall.dao.user;


import cn.jdcloud.medicine.mall.domain.user.User;
import cn.jdcloud.medicine.mall.domain.user.UserDto;
import cn.jdcloud.medicine.mall.domain.user.UserExcel;
import cn.jdcloud.medicine.mall.domain.user.UserResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<UserResult> listUser(@Param("page")Page<UserResult> page, @Param("userDto")UserDto userDto);

    Integer listCount(@Param("userDto")UserDto userDto);

    List<UserExcel> listAll();

    int insertList(List<User> list);
}