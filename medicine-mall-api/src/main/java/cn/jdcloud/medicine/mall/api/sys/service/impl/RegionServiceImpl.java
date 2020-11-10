package cn.jdcloud.medicine.mall.api.sys.service.impl;

import cn.jdcloud.framework.common.vo.VantSelector;
import cn.jdcloud.framework.core.constant.Constants;
import cn.jdcloud.medicine.mall.api.sys.service.RegionService;
import cn.jdcloud.medicine.mall.api.sys.vo.EleRegionVo;
import cn.jdcloud.medicine.mall.api.sys.vo.RegionVo;
import cn.jdcloud.medicine.mall.dao.sys.RegionMapper;
import cn.jdcloud.medicine.mall.domain.sys.Region;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionServiceImpl implements RegionService {

    protected Log log = LogFactory.getLog(getClass());

    @Resource
    private RegionMapper regionDao;

    @Resource(name = "gson")
    private Gson gson;

    /**
     * 根据父级获取地区
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Region> getRegionList(Integer parentId) {
        if(parentId == null) {
            parentId = 1;
        }
        return regionDao.getRegionList(parentId);
    }

    @Override
    public Map<String, Object> getRegionListH5() {
        Map<String, Object> bigMap = new HashMap<>();
        List<Region> list = regionDao.getRegionListH5();
        List<Map<String,Object>> regionVos = new ArrayList<>();
        List<RegionVo> vos = new ArrayList<>();
        for (Region pRegion : list) {
            List<Region> regionList = regionDao.getRegionList(pRegion.getId());
            //最里层的list
            List<RegionVo> voList = new ArrayList<>();
            for (Region cRegion :regionList) {
                RegionVo regionVo = new RegionVo();
                regionVo.setId(cRegion.getId());
                regionVo.setName(cRegion.getShortName());
                voList.add(regionVo);
            }
            //定义中间的map
            Map<String, Object> cenMap = new HashMap<>();
            cenMap.put("pid", pRegion.getId());
            cenMap.put("cityList", voList);
            regionVos.add(cenMap);
            RegionVo regionVo = new RegionVo();
            regionVo.setId(pRegion.getId());
            regionVo.setName(pRegion.getShortName());
            vos.add(regionVo);
        }
        bigMap.put("province", vos);
        bigMap.put("city", regionVos);
        return bigMap;
    }

    @Override
    public List<EleRegionVo> getEleRegionList() {
        List<Region> list = regionDao.getRegionListH5();
        List<EleRegionVo> lv = new ArrayList<>(list.size());
        for (Region pRegion : list) {
            List<Region> regionList = regionDao.getRegionList(pRegion.getId());
            List<EleRegionVo> children = new ArrayList<>(regionList.size());
            for (Region cRegion :regionList) {
                EleRegionVo regionVo = new EleRegionVo(cRegion);
                children.add(regionVo);
            }
            EleRegionVo regionVo = new EleRegionVo(pRegion);
            regionVo.setChildren(children);
            lv.add(regionVo);
        }
        return lv;
    }

    @Override
    public void replaceCentralCity(List<Region> list) {
        if(list!=null&&list.size()>0){
            for (Region re:list) {
                //直辖市
                if(re.getIsCentral()== Constants.COMMON_IS){
                    List<Region> clist = regionDao.getRegionList(re.getId());
                    if(clist.size()>1){
                        log.error("data enums: have two central city,but expect one city name:"+re.getName());
                    }
                    try {
                        Region central = clist.get(0);
                        BeanUtils.copyProperties(re,central);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 根据code获取地区名称
     *
     * @param regionId
     * @return
     */
    @Override
    public String getRegionName(int regionId) {
        Region region = regionDao.getRegionById(regionId);
        String address = region.getName();
        while(region.getLevel() != 1) {
            region = regionDao.getRegionById(region.getParentId());
            address = region.getName() + address;

        }
        return address;

    }

    @Override
    public List<VantSelector> vantSelectList() {
        List<VantSelector> list = new ArrayList<>();
        //获取一级
        List<Region> plist = regionDao.getRegionList(1);
        for (Region parent :plist) {
            List<Region> children = regionDao.getRegionList(parent.getId());
            VantSelector vs = wapper(parent);
            vs.setChildren(wapperList(children));
            list.add(vs);
        }
        return list;
    }

    private VantSelector wapper(Region r){
        if(r ==null){
            return  null;
        }
        VantSelector vs = new VantSelector();
        vs.setId(r.getId());
        vs.setText(r.getShortName());
        vs.setValue(r.getId().toString());
        return vs;
    }

    private List<VantSelector> wapperList(List<Region> list){
        List<VantSelector> vl = new ArrayList<>();
        for (Region p :list) {
            if(p!=null){
                vl.add(wapper(p));
            }
        }
        return vl;
    }
}
