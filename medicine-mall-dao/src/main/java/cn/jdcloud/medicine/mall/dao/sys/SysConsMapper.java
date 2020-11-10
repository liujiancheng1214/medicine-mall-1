package cn.jdcloud.medicine.mall.dao.sys;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.sys.SysCons;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;


public interface SysConsMapper extends BaseMapper<SysConsMapper>{

    int insert(SysCons sysCons);

    int update(SysCons sysCons);

    int delete(SysCons sysCons);

    SysCons get(String idKey);

    List<SysCons> getAll();

    String getValue(String idKey);

    List<SysCons> getListByModule(String module);

    @MapKey("idKey")
    Map<String,SysCons> getMap(String module);

    List<SysCons> listAll(SysCons sysCons);

    void deleteByIdKey(String idKey);

    SysCons  findByIdKey(String idKey);


}