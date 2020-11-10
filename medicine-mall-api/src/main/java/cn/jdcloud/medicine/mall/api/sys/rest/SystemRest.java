package cn.jdcloud.medicine.mall.api.sys.rest;

import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.sys.service.RegionService;
import cn.jdcloud.medicine.mall.api.sys.service.SysDictService;
import cn.jdcloud.medicine.mall.api.sys.vo.SysDictVo;
import cn.jdcloud.medicine.mall.api.token.service.TokenService;
import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.sys.vo.EleRegionVo;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.domain.sys.Region;
import cn.jdcloud.medicine.mall.domain.sys.SysDict;
import cn.jdcloud.medicine.mall.domain.token.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 公共系统类接口
 */
@RestController
@RequestMapping(value = "/sys")
@Api(tags = "公共系统类接口")
public class SystemRest extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(SystemRest.class);

    @Resource
    private TokenService tokenService;

    @Resource
    private RegionService regionService;

    @Resource
    private SysDictService sysDictService;

//    /**
//     * 异常统一处理
//     * @param code  错误代码
//     * @param msg   错误信息
//     * @return  异常
//     * @throws Exception
//     */
//    @RequestMapping(value = "/enums")
//    @LoginRequired(value = false)
//    public ResponseVo enums(@RequestParam("code") Integer code,
//                            @RequestParam("msg")String msg) throws Exception {
//        return new ResponseVo(code,msg);
//    }

    /**
     * 刷新Token
     * @param userId 用户ID
     * @param refreshToken 用于刷新用的token
     * @return token对象
     * @throws Exception
     */
    @ApiOperation("刷新Token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    @LoginRequired(value = false)
    public ApiResult refreshToken(@RequestParam(value = "userId", required = true)int userId,
                                  @RequestParam(value = "userType", required = true)byte userType,
                                  @RequestParam(value = "refreshToken", required = true)String refreshToken) throws Exception {
    	Token token = tokenService.refreshToken(userId,refreshToken,userType);
    	if(token==null){
            throw new ApiException(UserCode.LOGIN_IS_INVALID);
        }
        return ApiResult.ok(token);
    }

    /**
     * 校验用户是否登录
     * @return 是否登录
     * @throws Exception
     */
    @ApiOperation("校验用户是否登录")
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    @LoginRequired(value = false)
    public ApiResult checkLogin(HttpServletRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");
        //从请求头取出userId 如果没有的话就是未登陆的状态 返回异常
        String uidStr = request.getHeader("userId");
        //从请求头取出userId 如果没有的话就是未登陆的状态 返回异常
        String type = request.getHeader("userType");
        //如果userID是空
        boolean login = false;
        if(StringUtils.isEmpty(uidStr) || !StringUtils.isNumeric(uidStr)) {
            login = false;
        }
        //TOKEN是否为空
        if (accessToken == null) {
            login = false;
        }
        //判断token是否合法
        if(accessToken.length()!=32){
            login = false;
        }
        Integer uid = Integer.parseInt(uidStr);
        byte userType = Byte.parseByte(type);
        //如果请求头里的token和缓存里的token不一样的话 返回异常
        login = tokenService.checkLogin(uid, accessToken,userType);
        return ApiResult.ok(login);
    }

    /**
     * 获取地区 (App)
     * @param parentId 父级地址ID   查询省（一级地址） 不用带这个参数   查询市级带省的ID即可  县级带市的ID
     * @return 地区List集合
     */
    @RequestMapping(value = "/regionList", method = RequestMethod.GET)
    @LoginRequired(value = false)
    @ApiOperation("获取地区1")
    public ApiResult regionList (@RequestParam(value = "parentId", required = false) Integer parentId) {
        List<Region> list = regionService.getRegionList(parentId);
        regionService.replaceCentralCity(list);
        return ApiResult.ok(list);

    }

    /**
     * 获取地区（H5)
     * @return
     */
    @RequestMapping(value = "/regionListH5", method = RequestMethod.GET)
    @LoginRequired(value = false)
    @ApiOperation("获取地区2")
    public ApiResult regionList(){
        Map<String, Object> map = regionService.getRegionListH5();
        return ApiResult.ok(map);
    }

    /**
     * 获取地区（element組件)
     * @return
     */
    @RequestMapping(value = "/regionListElement", method = RequestMethod.GET)
    @LoginRequired(value = false)
    @ApiOperation("获取地区-element组件")
    public ApiResult regionListElement(){
        List<EleRegionVo> lv = regionService.getEleRegionList();
        return ApiResult.ok(lv);
    }

    /**
     * 根据类型获取字典列表(不包含禁用的)
     * @return
     */
    @GetMapping(value = "/dict")
    @LoginRequired(value = false)
    @ApiOperation("根据type获取字典列表")
    public ApiResult getDictByType(String type){
        return ApiResult.ok(sysDictService.getDictByType(type));
    }
    /**
     * 根据类型获取字典列表 (包含禁用的)
     * @return
     */
    @GetMapping(value = "/dict/containDeleted")
    @LoginRequired(value = false)
    @ApiOperation("根据type获取字典列表")
    public ApiResult getDictContainDeletedByType(String type){
        return ApiResult.ok(sysDictService.getDictContainDeletedByType(type));
    }

    @PostMapping(value = "/updateDict")
    @ApiOperation("更新字典")
    public ApiResult updateDict(SysDict dict){
        sysDictService.updateDict(dict);
        return ApiResult.ok();
    }
    @PostMapping(value = "/addDict")
    @ApiOperation("添加字典")
    public ApiResult addDict(SysDictVo dict){
        sysDictService.addDict(dict);
        return ApiResult.ok();
    }
    @GetMapping(value = "/reInitSysDict")
    @ApiOperation("重新初始化字典（重新读库）")
    public ApiResult reInitSysDict(){
        sysDictService.reInitSysDict();
        return ApiResult.ok();
    }
}
