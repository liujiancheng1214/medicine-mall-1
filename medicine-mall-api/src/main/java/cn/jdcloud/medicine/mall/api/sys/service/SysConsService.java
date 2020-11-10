package cn.jdcloud.medicine.mall.api.sys.service;

import cn.jdcloud.medicine.mall.api.sys.dto.SysConsDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description: 常量接口
 * @author: yanghuoyun
 * @since: 2017/4/10 11:20
 * @version: v1.0
 */
public interface SysConsService {

    /**
     * 查询出发地所有1级地名
     * @return 1级地名列表
     */
    Map<String,String> getKeyValueMap();

    void saveSysCons(SysConsDto sysConsDto);

    List<ConsModuleVo> listModules();

    void deleteSysCons(SysConsDto sysConsDto);


    @Data
    public static class ConsModuleVo{
        private String id;
        private String module;
        public ConsModuleVo(){
        }
        public ConsModuleVo(String id,String module){
            this.id = id;
            this.module = module;
        }
    }


}