package cn.jdcloud.medicine.mall.api.sys.vo;

import cn.jdcloud.medicine.mall.domain.sys.Region;
import lombok.Data;

import java.util.List;

/**
 * Created by xieqiujin on 2017/8/19.
 */
@Data
public class EleRegionVo {
    private Integer id;
    private String value;
    private String label;
    private List<EleRegionVo> children;
    
    public EleRegionVo (Region region){
        this.id = region.getId();
        this.value = String.valueOf(region.getId());
        this.label = region.getShortName();
    }

	public EleRegionVo() {
		super();
	}
}
