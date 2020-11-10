package cn.jdcloud.medicine.mall.api.biz.user.service;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import cn.jdcloud.medicine.mall.domain.user.UserImgVO;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddVo;
import cn.jdcloud.medicine.mall.domain.user.User;
import cn.jdcloud.medicine.mall.domain.user.UserDto;
import cn.jdcloud.medicine.mall.domain.user.UserResult;

/**
 * @desc 用户业务接口
 */
public interface UserService extends IService<User>{


	User  queryUserByMobile(String mobil);
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username,String password);


	/**
	 * 用户注册
	 * @param userAddVo
	 * @return
	 */
	User regist(UserAddVo userAddVo);

    /**
     * 查询客户列表
     * @param page
     * @param userDto
     * @return
     */
    Map listUser(Page<UserResult> page, UserDto userDto);

    /**
     * 查询所有用户（下拉框）
     * @return
     */
    List<Map> getAllUser(String userName);

    /**
     * 查询所有地址（下拉框）
     * @return
     */
    List<Map> getRegion();

    /**
     * 查询客户根据id
     * @param userId
     * @return
     */
    User getUser(Integer userId);

    /**
     * 更新客户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 导出Excel表格（导出所有客户信息）
     * @param response
     * @param type 0表示导出数据  1表示下载模板
     */
    void exportExcel(HttpServletResponse response, String type);

    /**
     * 导入Excel，客户信息批量插入数据库
     * @param file
     * @return
     * @throws IOException
     */
    Map readExcel(MultipartFile file) throws IOException;

    /**
     * 获取用户图片
     * @param userId
     * @return: java.util.List<cn.jdcloud.medicine.mall.domain.user.UserImgVO>
     * @author: HuXuNing
     * @date: 2020/11/10 22:12
     */
    Set<UserImgVO> getUserImg(Integer userId);
}
