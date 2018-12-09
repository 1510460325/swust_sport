package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.Sport_InfoDao;
import cn.wzy.sport.entity.Sport_Info;
import cn.wzy.sport.service.Sport_InfoService;
import cn.wzy.sport.service.VO.Sport_InfoVO;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.PropertiesUtil;
import org.cn.wzy.util.StreamsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.util.Date;
import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/20 15:43
 * 不短不长八字刚好
 */
@Service
public class Sport_InfoServiceImpl implements Sport_InfoService {

	@Autowired
	private Sport_InfoDao sport_infoDao;

	@Override
	public Integer deleteSport(Integer id) {
		return sport_infoDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer insert(Sport_InfoVO record) {
		Sport_Info sport_info = new Sport_Info();
		BeanUtils.copyProperties(record,sport_info);
		String module = PropertiesUtil.StringValue("module");
		if (record.getFile1() != null) {
			String fileName = System.currentTimeMillis() + "sport.jpg";
			if (StreamsUtil.download(module, fileName, record.getFile1()))
				sport_info.setSpImg("/module/" + fileName);
		}
		String room = PropertiesUtil.StringValue("room");
		if (record.getFile2() != null) {
			String fileName = System.currentTimeMillis() + "room.jpg";
			if (StreamsUtil.download(room, fileName, record.getFile2()))
				sport_info.setSpRoimg("/room/" + fileName);
		}
		return sport_infoDao.insertSelective(sport_info);
	}

	@Override
	public Integer update(Sport_InfoVO record) {
		Sport_Info sport_info = new Sport_Info();
		BeanUtils.copyProperties(record,sport_info);
		String module = PropertiesUtil.StringValue("module");
		if (record.getFile1() != null) {
			String fileName = System.currentTimeMillis() + "sport.jpg";
			if (StreamsUtil.download(module, fileName, record.getFile1()))
				sport_info.setSpImg("/module/" + fileName);
		}
		String room = PropertiesUtil.StringValue("room");
		if (record.getFile2() != null) {
			String fileName = System.currentTimeMillis() + "room.jpg";
			if (StreamsUtil.download(room, fileName, record.getFile2()))
				sport_info.setSpRoimg("/room/" + fileName);
		}
		return sport_infoDao.updateByPrimaryKeySelective(sport_info);
	}

	@Override
	public List<Sport_Info> querySports(BaseQuery<Sport_Info> query) {
		return sport_infoDao.selectByCondition(query);
	}

	@Override
	public int total(BaseQuery<Sport_Info> query) {
		return sport_infoDao.selectCountByCondition(query);
	}
}
