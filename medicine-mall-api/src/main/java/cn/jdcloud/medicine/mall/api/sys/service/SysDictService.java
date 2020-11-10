package cn.jdcloud.medicine.mall.api.sys.service;

import cn.jdcloud.medicine.mall.api.sys.vo.SysDictVo;
import cn.jdcloud.medicine.mall.domain.sys.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
public interface SysDictService extends IService<SysDict> {
    List<SysDict> getDictByType(String type);

    List<SysDict>  getDictContainDeletedByType(String type);

    void updateDict(final SysDict dict);

    void addDict(SysDictVo dict);

    void reInitSysDict();
}