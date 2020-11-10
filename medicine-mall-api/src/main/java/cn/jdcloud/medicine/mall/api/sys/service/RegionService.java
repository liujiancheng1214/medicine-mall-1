package cn.jdcloud.medicine.mall.api.sys.service;

import cn.jdcloud.framework.common.vo.VantSelector;
import cn.jdcloud.medicine.mall.api.sys.vo.EleRegionVo;
import cn.jdcloud.medicine.mall.domain.sys.Region;

import java.util.List;
import java.util.Map;

/**
 * 地区
 */
public interface RegionService {

    /**
     * 根据父级获取地区
     * @param parentId
     * @return
     */
    List<Region> getRegionList(Integer parentId);

    /**
     * 微信公众号H5获取地区
     * @return
     */
    Map<String, Object>  getRegionListH5();

    /**
     * element组件-获取地区
     * @return
     */
    List<EleRegionVo> getEleRegionList();

    /**
     * 替换直辖市
     * @param
     * @return
     */
    void replaceCentralCity(List<Region> list);


    /**
     * 根据code获取地区名称
     * @param regionId
     * @return
     */
    String getRegionName(int regionId);

    List<VantSelector> vantSelectList();

}
