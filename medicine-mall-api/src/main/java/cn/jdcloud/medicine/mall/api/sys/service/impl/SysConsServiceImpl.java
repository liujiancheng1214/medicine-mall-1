package cn.jdcloud.medicine.mall.api.sys.service.impl;

import cn.jdcloud.medicine.mall.api.sys.dto.SysConsDto;
import cn.jdcloud.medicine.mall.api.sys.service.SysConsService;
import cn.jdcloud.medicine.mall.dao.sys.SysConsMapper;
import cn.jdcloud.medicine.mall.domain.sys.ConsEnum;
import cn.jdcloud.medicine.mall.domain.sys.SysCons;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 常量接口
 * @author: yanghuoyun
 * @since: 2017/4/10 11:20
 * @version: v1.0
 */
@Service("sysConsServiceImpl")
public class SysConsServiceImpl implements SysConsService {

    @Resource
    private SysConsMapper sysConsDao;
    /**
     * 查询出发地所有1级地名
     * @return 1级地名列表
     */
    @Override
    public Map<String,String> getKeyValueMap(){
        Map<String,String> map = new HashMap<>();
        List<SysCons> list = sysConsDao.getAll();
        if(list ==null || list.size()==0){
            return map;
        }
        for (SysCons sc:list) {
            if(sc!=null){
                map.put(sc.getIdKey(),sc.getValue());
            }
        }
        return map;
    }



    @Override
    public List<ConsModuleVo> listModules(){
        ConsEnum[] values = ConsEnum.values();
        List<ConsModuleVo> consModuleVoList = Lists.newArrayList();
        for (ConsEnum value : values) {
            consModuleVoList.add(new ConsModuleVo(value.toString(),value.toString()));
        }
        return consModuleVoList;
    }

    @Override
    public void saveSysCons(SysConsDto sysConsDto){
        SysCons sysCons = sysConsDao.findByIdKey(sysConsDto.getIdKey());
      //  ValidUtil.assertIf(!(sysCons==null), CommonErrorEnums.SYS_CONS_IS_EXIST);
        sysCons = new SysCons();
        BeanUtils.copyProperties(sysConsDto, sysCons);
        sysConsDao.insert(sysCons);
    }

    @Override
    public void deleteSysCons(SysConsDto sysConsDto){
        String idKey = sysConsDto.getIdKey();
        sysConsDao.deleteByIdKey(idKey);
    }

}