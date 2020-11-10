package cn.jdcloud.medicine.mall.api.sys.service.impl;

import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.sys.service.SysDictService;
import cn.jdcloud.medicine.mall.api.sys.vo.SysDictVo;
import cn.jdcloud.medicine.mall.dao.admin.RoleMapper;
import cn.jdcloud.medicine.mall.dao.sys.SysDictMapper;
import cn.jdcloud.medicine.mall.domain.sys.SysDict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Resource
    SysDictMapper sysDictMapper;

    private Map<String, List<SysDict>> sysDict;

    @PostConstruct
    private void initDict(){
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("type","sort");
        List<SysDict> dictList = this.list(queryWrapper);
        sysDict = dictList.stream().collect(Collectors.groupingBy(SysDict::getType));
    }

    /**
     * 通过 类型 获取字典 list  （包含 禁用的（ idDeleted  = 1 ）的）
     * @param type
     * @return
     */
    @Override
    public List<SysDict> getDictContainDeletedByType(String type) {
        return sysDict.get(type);
    }

    /**
     * 通过 类型 获取字典 list  （不包含 禁用的（ idDeleted  = 1 ）的）
     * @param type
     * @return
     */
    @Override
    public List<SysDict> getDictByType(String type) {
        List<SysDict> list = sysDict.get(type).stream().filter(a -> (a.getIsDeleted() !=1)).collect(Collectors.toList());
        return list;
    }

    @Override
    public void updateDict(final SysDict dict){
        synchronized(this)
        {
            this.updateById(dict);
            SysDict finalDict  = this.getById(dict.getId());
            List<SysDict> dicts = sysDict.get(finalDict.getType());
            dicts.forEach(x->{
                if (x.getId().equals(finalDict.getId())){
                    x = finalDict;
                    return;
                }
            });
        }
    }

    @Override
    public void addDict(SysDictVo dict){

        if (StringUtils.isBlank(dict.getType())){
            throw new ApiException(SysCodeEnums.PARAMS_IS_NULL);
        }
        synchronized(this)
        {
            SysDict dict1 = new SysDict();
            dict1.setIsMultiple(dict.getIsMultiple());
            dict1.setIsDeleted(SysDict.NUMBER_BYTE_ZERO);
            dict1.setCreateTime(new Date());
            dict1.setUpdateTime(new Date());
            dict1.setName(dict.getName());
            dict1.setSort(dict.getSort());
            dict1.setRemark(dict.getRemark());
            dict1.setValue(dict.getValue());
            sysDictMapper.insert(dict1);

            List<SysDict> dicts = sysDict.get(dict.getType());
            if (dicts!=null){
                dicts.add(dict1);
                return;
            }else {
                sysDict.put(dict.getType(), new ArrayList<SysDict>(){{add(dict1);}});
                return;
            }
        }
    }

    @Override
    public void reInitSysDict(){
        synchronized(sysDict)
        {
            this.initDict();
        }
    }
}
