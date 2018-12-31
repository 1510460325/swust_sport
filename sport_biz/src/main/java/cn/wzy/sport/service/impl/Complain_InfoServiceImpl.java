package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.Complain_InfoDao;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.entity.Complain_Info;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.Complain_InfoService;
import cn.wzy.sport.service.model.ComplainVO;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Create by Wzy
 * on 2018/7/20 22:04
 * 不短不长八字刚好
 */
@Service
public class Complain_InfoServiceImpl implements Complain_InfoService {

	@Autowired
	private Complain_InfoDao complain_infoDao;

	@Autowired
	private User_InfoDao userInfoDao;

	@Override
	public List<ComplainVO> queryAllComplains() {
		List<Complain_Info> list = complain_infoDao.selectByCondition(new BaseQuery<Complain_Info>(Complain_Info.class));
		if (list != null && list.size() != 0) {
			int size = list.size();
			List<Integer> ids = new ArrayList<>(size);
			List<ComplainVO> result = new ArrayList<>(size);
			for (Complain_Info vo : list) {
				ComplainVO one = new ComplainVO();
				BeanUtils.copyProperties(vo, one);
				ids.add(one.getCoUserid());
				result.add(one);
			}
			List<User_Info> user_infos = userInfoDao.selectByIds(ids);
			Map<Integer, String> userNameMap = new HashMap<>(size);
			for (User_Info user : user_infos) {
				userNameMap.put(user.getId(), user.getUsName());
			}
			for (ComplainVO complainVO : result) {
				complainVO.setUsName(userNameMap.get(complainVO.getCoUserid()));
			}
			return result;
		}
		return null;
	}

	@Override
	public Integer deleComplain(Integer id) {
		return complain_infoDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean insert(Complain_Info record) {
		record.setCoCreatdate(new Date());
		return complain_infoDao.insert(record) == 1;
	}
}
