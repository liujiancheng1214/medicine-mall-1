package cn.jdcloud.medicine.mall.api.sys.service.impl;

import cn.jdcloud.medicine.mall.api.sys.service.ExpressCompanyService;
import cn.jdcloud.medicine.mall.dao.sys.ExpressCompanyMapper;
import cn.jdcloud.medicine.mall.domain.sys.ExpressCompany;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Vector;

/**
 * 快递公司
 *
 * @author HuZhengYu
 * @date 15:02 2020/8/26
 */
@Service("ExpressCompanyService")
public class ExpressCompanyServiceImpl implements ExpressCompanyService {

    protected Log log = LogFactory.getLog(getClass());

    @Resource
    private ExpressCompanyMapper expressCompanyMapper;

    @Override
    public List<ExpressCompanyVo> list() {
        List<ExpressCompanyVo> list = new Vector<>();
        List<ExpressCompany> companies =
                expressCompanyMapper.selectList(new QueryWrapper<ExpressCompany>().eq("is_deleted", 0));
        if (CollectionUtils.isNotEmpty(companies)) {
            companies.forEach(company -> {
                list.add(new ExpressCompanyService.ExpressCompanyVo(company));
            });
        }
        return list;
    }
}
