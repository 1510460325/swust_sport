package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.Sport_InfoDao;
import cn.wzy.sport.entity.Sport_Info;
import cn.wzy.sport.service.Sport_InfoService;
import cn.wzy.sport.service.util.FileUtil;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public Integer insert(HttpServletRequest request, Sport_Info sport_info, String file) {
        sport_info.setSpCreatdate(new Date());
        if (file == null) {
            sport_info.setSpImg("/static/img/logo.png");
            return sport_infoDao.insertSelective(sport_info);
        }
        String relativePath = PropertiesUtil.StringValue("upload_path");
        String path = request.getServletContext().getRealPath(relativePath);
        String fileName = System.currentTimeMillis()+".jpg";
        if (FileUtil.download(path,fileName,file))
            sport_info.setSpImg(relativePath + "/" + fileName);
        return sport_infoDao.insertSelective(sport_info);
    }

    @Override
    public Integer update(HttpServletRequest request,Sport_Info sport_info, String file) {
        if (file == null)
            return sport_infoDao.updateByPrimaryKeySelective(sport_info);
        String relativePath = PropertiesUtil.StringValue("upload_path");
        String path = request.getServletContext().getRealPath(relativePath);
        String fileName = System.currentTimeMillis()+".jpg";
        if (FileUtil.download(path,fileName,file))
            sport_info.setSpImg(relativePath + "/" + fileName);
        return sport_infoDao.updateByPrimaryKeySelective(sport_info);
    }

    @Override
    public List<Sport_Info> querySports(Sport_Info sport_info) {
        BaseQuery<Sport_Info> query = new BaseQuery<>();
        query.setQuery(sport_info);
        return sport_infoDao.selectByCondition(query);
    }
}
