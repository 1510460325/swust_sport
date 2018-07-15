package cn.wzy.util;

import org.cn.wzy.util.MapperingGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by Wzy
 * on 2018/7/14 11:27
 * 不短不长八字刚好
 */
public class Mapperutil extends BaseDaoTest {

    @Autowired
    private MapperingGenerator mapperingGenerator;

    @Test
    public void run() {
        mapperingGenerator.oldPath = "G:\\swust_sport\\sport_dao\\src\\main\\resources\\mapper";
        mapperingGenerator.implPath = mapperingGenerator.oldPath + "\\impl";
        mapperingGenerator.sql = mapperingGenerator.oldPath + "\\sql";
        mapperingGenerator.run();
    }
}
