package cn.jdcloud.medicine.mall.api.sys.rest;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.sys.service.ExpressCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuZhengYu
 * @description 公共系统类接口-快递公司
 * @date 14:58 2020/8/26
 */
@RestController
@RequestMapping(value = "/pub/expressCompany")
public class ExpressCompanyRest {

    @Autowired
    private ExpressCompanyService expressCompanyService;

    /**
     * 查询全部的快递公司
     *
     * @return cn.jdcloud.framework.core.vo.ApiResult
     * @author HuZhengYu
     * @date 15:13 2020/8/26
     */
    @GetMapping(value = "/list")
    public ApiResult list() {
        List<ExpressCompanyService.ExpressCompanyVo> list = expressCompanyService.list();
        return ApiResult.ok(list);
    }

}
