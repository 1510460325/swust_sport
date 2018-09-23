package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.Sport_InfoDao;
import cn.wzy.sport.entity.Sport_Info;
import cn.wzy.sport.service.Sport_InfoService;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.PropertiesUtil;
import org.cn.wzy.util.StreamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Integer insert(Sport_Info sport_info, String file1, String file2) {
		sport_info.setSpCreatdate(new Date());
		String module = PropertiesUtil.StringValue("module");
		if (file1 != null) {
			String fileName = System.currentTimeMillis() + "sport.jpg";
			if (StreamsUtil.download(module, fileName, file1))
				sport_info.setSpImg("/module/" + fileName);
		}
		String room = PropertiesUtil.StringValue("room");
		if (file2 != null) {
			String fileName = System.currentTimeMillis() + "room.jpg";
			if (StreamsUtil.download(room, fileName, file2))
				sport_info.setSpRoimg("/room/" + fileName);
		}
		return sport_infoDao.insertSelective(sport_info);
	}

	@Override
	public Integer update(Sport_Info sport_info, String file1, String file2) {
		String module = PropertiesUtil.StringValue("module");
		if (file1 != null) {
			String fileName = System.currentTimeMillis() + "sport.jpg";
			if (StreamsUtil.download(module, fileName, file1))
				sport_info.setSpImg("/module/" + fileName);
		}
		String room = PropertiesUtil.StringValue("room");
		if (file2 != null) {
			String fileName = System.currentTimeMillis() + "room.jpg";
			if (StreamsUtil.download(room, fileName, file2))
				sport_info.setSpRoimg("/room/" + fileName);
		}
		return sport_infoDao.updateByPrimaryKeySelective(sport_info);
	}

	@Override
	public List<Sport_Info> querySports(Sport_Info sport_info) {
		BaseQuery<Sport_Info> query = new BaseQuery<>();
		query.setQuery(sport_info);
		return sport_infoDao.selectByCondition(query);
	}
}
