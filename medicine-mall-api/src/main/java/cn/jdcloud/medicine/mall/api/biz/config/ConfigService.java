package cn.jdcloud.medicine.mall.api.biz.config;

import cn.jdcloud.medicine.mall.domain.config.Config;

public interface ConfigService {

	Config queryByCode(String code);
}
