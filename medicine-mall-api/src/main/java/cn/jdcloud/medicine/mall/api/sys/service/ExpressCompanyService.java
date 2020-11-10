package cn.jdcloud.medicine.mall.api.sys.service;

import cn.jdcloud.medicine.mall.domain.sys.ExpressCompany;
import lombok.Data;

import java.util.List;

/**
 * 快递公司
 *
 * @author HuZhengYu
 * @date 15:01 2020/8/26
 */
public interface ExpressCompanyService {

    /**
     * 查询全部的快递公司
     *
     * @return java.util.List<cn.jdcloud.medicine.mall.api.sys.service.ExpressCompanyService.ExpressCompanyVo>
     * @author HuZhengYu
     * @date 15:13 2020/8/26
     */
    List<ExpressCompanyVo> list();

    @Data
    class ExpressCompanyVo{
        private Integer id;
        private String name;
        public ExpressCompanyVo(){
        }
        public ExpressCompanyVo(ExpressCompany company) {
            this.id = company.getId();
            this.name = company.getName();
        }
    }

}
