package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.jdcloud.medicine.mall.domain.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.api.biz.user.utils.EncryptUtils;
import cn.jdcloud.medicine.mall.api.biz.user.utils.ExcelUtil;
import cn.jdcloud.medicine.mall.api.biz.user.vo.UserAddVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.sys.RegionMapper;
import cn.jdcloud.medicine.mall.dao.user.UserAddressMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.sys.Region;

/**
 * @author chenQF
 * @desc 用户相关业务层实现
 * @date 2020/8/14 0014 13:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    UserMapper userMapper;
    @Resource
    RegionMapper regionMapper;
    @Resource
    UserAddressMapper userAddressMapper;

    @Override
    public Map listUser(Page<UserResult> page, UserDto userDto) {
        //查询开户状态的数量
        Map map = new HashMap();
        map.put("account_status", 0);
        Map map1 = new HashMap();
        map1.put("account_status", 1);
        page.setRecords(userMapper.listUser(page, userDto));
        page.setTotal(userMapper.listCount(userDto));

        Map result = new HashMap();
        result.put("openSize", userMapper.selectByMap(map).size()); //已开户数量
        result.put("noOpenSize", userMapper.selectByMap(map1).size()); //未开户数量
        result.put("data",page); //分页数据

        return result;
    }

    @Override
    public List<Map> getAllUser(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("company_name", userName);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            Map map = new HashMap();
            map.put("value", users.get(i).getId());
            map.put("label", users.get(i).getCompanyName());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map> getRegion() {
        List<Map> mapList = new ArrayList<>();
        List<Region> regions = regionMapper.getRegion();

        //拿出所有的省级数据
        List<Region> provinces = regions.stream().filter(f -> f.getParentId() == 1)
                .collect(Collectors.toList());
        //省份
        List<Map> provinceList = new ArrayList<>();
        for (Region province : provinces) {
            //省份下的城市
            List<Region> collect = regions.stream().filter(f -> f.getParentId().equals(province.getId()))
                    .collect(Collectors.toList());

            Map pMap = new HashMap();
            pMap.put("value", province.getId().toString());
            pMap.put("label", province.getName());
            //城市
            List<Map> cityList = new ArrayList<>();
            for (Region city : collect) {
                //城市下的所有区
                List<Region> collect1 = regions.stream().filter(f -> f.getParentId().equals(city.getId()))
                        .collect(Collectors.toList());
                //区
                List<Map> areaList = new ArrayList<>();
                for (Region area : collect1) {
                    Map dMap = new HashMap();
                    dMap.put("value", area.getId().toString());
                    dMap.put("label", area.getName());
                    areaList.add(dMap);
                }
                //城市
                Map cMap = new HashMap();
                cMap.put("value", city.getId().toString());
                cMap.put("label", city.getName());
                cMap.put("children", areaList);
                cityList.add(cMap);
            }
            pMap.put("children", cityList);
            provinceList.add(pMap);
        }

        return provinceList;
    }

    @Override
    public User getUser(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public void updateUser(User user) {
        User userD = userMapper.selectById(user.getId());
        if (userD == null) {
            throw new ApiException(UserCode.USER_BASE_NOT_EXIST);
        }
        userMapper.updateById(user);
    }

    @Override
    public void exportExcel(HttpServletResponse response, String type) {
        String[] headers = {"客户编码"
                , "手机号码"
                , "客户名称"
                , "联系人姓名"
                , "联系人手机"
                , "联系人地址"
//                , "客户类型"
//                , "省份"
//                , "城市"
//                , "区县"
//                , "客户等级"
//                , "税号"
//                , "发票类型"
              };
        String fileName = "用户表";
        //查询需要导出的用户数据
        List<UserExcel> userExcels = new ArrayList<>();
        if ("0".equals(type)) { //0表示导出数据  1表示下载模板
            userExcels = userMapper.listAll();
            if(userExcels.size() > 0){
                for (int i = 0; i < userExcels.size(); i++) {
                    UserExcel ue = userExcels.get(i);
                    if(ue.getType().equals("0")){
                        ue.setType("普通客户");
                    }else{
                        ue.setType("PLUS客户");
                    }
                    if(ue.getInvoiceType().equals("0")){
                        ue.setInvoiceType("普通发票");
                    }else{
                        ue.setInvoiceType("电子发票");
                    }
                    if(ue.getStatus().equals("0")){
                        ue.setStatus("可用");
                    }else{
                        ue.setStatus("已冻结");
                    }
                }
            }
            String[] headers1 = {"客户编码"
                    , "手机号码"
                    , "客户名称"
                    , "联系人姓名"
                    , "联系人手机"
                    , "联系人地址"
                    , "客户类型"
                    , "省份"
                    , "城市"
                    , "区县"
                    , "客户等级"
                    , "税号"
                    , "发票类型"
                    , "客户状态"
                    , "注册时间"
                    , "更新时间"};
            headers = headers1;
        }
        Map<String, Object> studentMap = new HashMap();
        studentMap.put("headers", headers);
        studentMap.put("dataList", userExcels);
        studentMap.put("fileName", fileName);

        List<Map> mapList = new ArrayList();
        mapList.add(studentMap);
        ExcelUtil.exportMultisheetExcel(fileName, mapList, response);
    }

    @Override
    public Map readExcel(MultipartFile file) throws IOException {
        List<Map<String, String>> mapList = ExcelUtil.readExcel(file, 0);
        List<User> list = new ArrayList<>();
        //失败的数据
        List<String> errorList = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> data = mapList.get(i);
            //必填项为空，此条数据导入失败
            if (!StringUtils.isNotBlank(data.get("客户名称")) || !StringUtils.isNotBlank(data.get("手机号码"))) {
                errorList.add(data.get("客户名称"));
                continue;
            }
//            if (!StringUtils.isNotBlank(data.get("客户类型")) || !StringUtils.isNotBlank(data.get("省份"))
//                    || !StringUtils.isNotBlank(data.get("城市")) || !StringUtils.isNotBlank(data.get("区县"))
//                    || !StringUtils.isNotBlank(data.get("客户等级"))) {
//                errorList.add(data.get("客户名称"));
//                continue;
//            }
            User user = new User();
            user.setHelpCode(data.get("客户编码"));
            user.setMobile(data.get("手机号码"));
            user.setCompanyName(data.get("客户名称"));
            user.setContactName(data.get("联系人姓名"));
            user.setContactPhone(data.get("联系人手机"));
            user.setContactAddress(data.get("联系人地址"));
//            user.setType((byte) Integer.parseInt(data.get("客户类型")));
//            user.setProvinceId(Integer.parseInt(data.get("省份")));
//            user.setCityId(Integer.parseInt(data.get("城市")));
//            user.setDistrictId(Integer.parseInt(data.get("区县")));
//            user.setTaxNo(data.get("税号"));
//            user.setInvoiceType(data.get("发票类型"));
//            user.setUserLevelId(Integer.parseInt(data.get("客户等级")));
//            user.setStatus((byte) 0);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            //密码加密
            String salt = StringUtils.randomUUID();
            String password = EncryptUtils.encryptPassword(salt, "888888");
            user.setSalt(salt);
            user.setPassword(password);
            list.add(user);
        }
        //成功的数据
        Map result = new HashMap();
        result.put("errorList", errorList); //错误数据
        result.put("errorCount", errorList.size()); //去掉一个表头
        result.put("successCount", list.size());
        if (list.size() > 0) {
            userMapper.insertList(list);
        }
        return result;
    }

    @Override
    public Set<UserImgVO> getUserImg(Integer userId) {
        User user = userMapper.selectById(userId);
        Set<UserImgVO> set = new HashSet<>();
        if (!StringUtils.isBlank(user.getCgwtssmj())) {
            set.add(new UserImgVO(user.getCgwtssmj(),"采购委托书扫描件.jpg"));
        }
        if (!StringUtils.isBlank(user.getCgysfsmj())) {
            set.add(new UserImgVO(user.getCgysfsmj(),"身份证扫描件.jpg"));
        }
        if (!StringUtils.isBlank(user.getGsp())) {
            set.add(new UserImgVO(user.getGsp(),"GSP.jpg"));
        }
        if (!StringUtils.isBlank(user.getYljgzyxkz())) {
            set.add(new UserImgVO(user.getYljgzyxkz(),"医疗机构执业许可证.jpg"));
        }
        if (!StringUtils.isBlank(user.getYyzz())) {
            set.add(new UserImgVO(user.getYyzz(),"营业执照.jpg"));
        }
        if (!StringUtils.isBlank(user.getYpjyxkz())) {
            set.add(new UserImgVO(user.getYpjyxkz(),"药品经营许可证.jpg"));
        }
        return set;
    }

    @Override
	public User login(String username, String password) {
		User user=queryUserByMobile(username);
		if(user==null) {
			throw new ApiException(UserCode.MOBILE_IS_NOT_EXISTS);
		}
		String salt=user.getSalt();
		password = EncryptUtils.encryptPassword(salt, password);
		if(StringUtils.equals(password, user.getPassword())) {
			return user;
		}
		 throw new ApiException(UserCode.PASSWORD_IS_ERROR);
	}

	@Override
	@Transactional
	public User regist(UserAddVo userAddVo) {
		User user=null;
		Date now=new Date();
		user=queryUserByMobile(userAddVo.getMobile());
		if(Objects.nonNull(user)) {
			throw new ApiException(UserCode.MOBILE_IS_EXISTS);
		}
	    user=new User();
		BeanUtil.copyProperties(userAddVo, user);
		String salt = StringUtils.randomUUID();
		user.setSalt(salt);
	    String password = EncryptUtils.encryptPassword(salt, userAddVo.getPassword());
		user.setCreateTime(now);
		user.setPassword(password);
		user.setId(0);
		this.saveOrUpdate(user);
		// 生成默认地址
		user=queryUserByMobile(user.getMobile());
		UserAddress address=new UserAddress();
		address.setId(0);
		address.setCityId(user.getCityId());
		address.setCityName(getName(user.getCityId()));
		address.setDistrictId(user.getDistrictId());
		address.setDistrictName(getName(user.getDistrictId()));
		address.setProvinceId(user.getProvinceId());
		address.setProvinceName(getName(user.getProvinceId()));
		address.setAddress(user.getCompanyAddress());
		address.setMobile(user.getContactPhone());
		address.setRealName(user.getContactName());
		address.setUserId(user.getId());
		address.setIsDefault((byte)1);
		address.setIsDeleted((byte)0);
		address.setCreateTime(now);
		userAddressMapper.insert(address);
		return user;
	}

	private String getName(Integer  id) {
		Region region=regionMapper.selectById(id);
		if(region!=null) {
			return region.getName();
		}
		return null;
	}

	@Override
	public User queryUserByMobile(String mobile) {
		QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
		queryWrapper.eq("mobile", mobile);
		User user=userMapper.selectOne(queryWrapper);
		return user;
	}

}
