package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.sys.service.RegionService;
import cn.jdcloud.medicine.mall.api.sys.vo.EleRegionVo;
import cn.jdcloud.medicine.mall.domain.sys.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/region")
@Api(tags = "地区接口")
public class RegionRest {

	@Autowired
	private RegionService regionService;

	@ApiOperation(value = "查询所有的省份")
	@GetMapping(value = "/province")
	public ApiResult<List<EleRegionVo>> province(){
		List<Region> list=regionService.getRegionList(1);
		List<EleRegionVo>voList=new ArrayList<EleRegionVo>();
		for(Region r:list) {
			voList.add(new EleRegionVo(r));
		}
		return ApiResult.ok(voList);
	}

	@ApiOperation(value = "查询省份的下一集")
	@GetMapping(value = "/subRegion")
	public ApiResult<List<EleRegionVo>> subRegion(Integer parentId){
		List<Region> list=regionService.getRegionList(parentId);
		List<EleRegionVo>voList=new ArrayList<EleRegionVo>();
		for(Region r:list) {
			voList.add(new EleRegionVo(r));
		}
		return ApiResult.ok(voList);
	}
}
