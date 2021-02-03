package cn.jdcloud.medicine.mall.api.biz.share.service.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jdcloud.medicine.mall.api.biz.config.ConfigService;
import cn.jdcloud.medicine.mall.api.biz.share.service.ShareService;
import cn.jdcloud.medicine.mall.api.constant.Constant;
import cn.jdcloud.medicine.mall.dao.share.ShareMapper;
import cn.jdcloud.medicine.mall.domain.config.Config;
import cn.jdcloud.medicine.mall.domain.share.Share;


@Service
public class ShareServiceImpl implements ShareService {

	@Autowired
	private ShareMapper shareMapper;
	@Autowired
	private ConfigService configService;
	
	@Override
	public void AddShare(Integer sponsorId) {
		Share share=new Share();
		share.setId(0);
		share.setCreateTime(new Date());
		share.setSponsorId(sponsorId);
		Config  config=configService.queryByCode(Constant.SHARE_CONPON);
		share.setConponId(Integer.parseInt(config.getValue()));
		shareMapper.insert(share);
	}

}
