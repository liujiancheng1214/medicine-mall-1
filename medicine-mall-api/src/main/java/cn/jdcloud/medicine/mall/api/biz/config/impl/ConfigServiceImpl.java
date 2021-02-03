package cn.jdcloud.medicine.mall.api.biz.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.jdcloud.medicine.mall.api.biz.config.ConfigService;
import cn.jdcloud.medicine.mall.dao.config.ConfigMapper;
import cn.jdcloud.medicine.mall.domain.config.Config;


@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public Config queryByCode(String code) {
		return configMapper.selectOne(new QueryWrapper<Config>().eq("code", code));
	}

}
