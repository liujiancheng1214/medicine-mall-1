package cn.jdcloud.medicine.mall.api.biz.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.medicine.mall.api.biz.product.service.ItemService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemVo;
import cn.jdcloud.medicine.mall.api.biz.user.service.FootprintService;
import cn.jdcloud.medicine.mall.api.biz.user.vo.FootprintVo;
import cn.jdcloud.medicine.mall.dao.user.FootprintMapper;
import cn.jdcloud.medicine.mall.domain.user.Footprint;

@Service
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, Footprint> implements FootprintService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FootprintMapper footprintMapper;

    @Override
    public List<FootprintVo> listFootprint(Integer userId, int pageNum, int pageSize) {
        List<FootprintVo> voList = new ArrayList<>();
        Page<Footprint> page = new Page<Footprint>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = this.page(page, new QueryWrapper<Footprint>().eq("user_id", userId).orderByDesc("create_time"));
        List<Footprint> list = page.getRecords();
        Map<String, FootprintVo> map = new HashMap<>();
        if (list != null && list.size() > 0) {
            for (Footprint footprint : list) {
                String time = DateUtils.formatDate(footprint.getCreateTime(), "yyyy-MM-dd");
                ItemVo itemVo = itemService.queryItemVoByItemNo(footprint.getItemNo());
                if (map.containsKey(time)) {
                    FootprintVo vo = map.get(time);
                    vo.getItemList().add(itemVo);
                } else {
                    FootprintVo vo = new FootprintVo();
                    vo.setTime(time);
                    List<ItemVo> tempList = new ArrayList<>();
                    tempList.add(itemVo);
                    vo.setItemList(tempList);
                    map.put(time, vo);
                }
            }
        }
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            FootprintVo footprintVo = map.get(key);
            voList.add(footprintVo);
        }
        return voList;
    }

    @Override
    public int deleteFootprint(Integer userId, List<Integer> ids) {
        List<Footprint> list = footprintMapper
                .selectList(new QueryWrapper<Footprint>().eq("user_id", userId).in("id", ids));
        List<Integer> idList = list.stream().map(bean -> bean.getId()).collect(Collectors.toList());
        return footprintMapper.deleteBatchIds(idList);
    }

    @Override
    public Footprint addFootprint(Integer userId, String itemNo) {
        Footprint footprint = null;
        List<Footprint> list = footprintMapper
                .selectList(new QueryWrapper<Footprint>().eq("user_id", userId).eq("item_no", itemNo));
        if (list.size() > 0) {
            footprint = list.get(0);
            footprint.setCreateTime(new Date());
            footprintMapper.updateById(footprint);
        } else {
            footprint = new Footprint();
            footprint.setId(0);
            footprint.setItemNo(itemNo);
            footprint.setUserId(userId);
            footprint.setCreateTime(new Date());
            footprintMapper.insert(footprint);
        }
        return footprint;
    }

}
