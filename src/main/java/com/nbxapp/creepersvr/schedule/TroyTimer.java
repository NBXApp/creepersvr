package com.nbxapp.creepersvr.schedule;


import com.nbxapp.creepersvr.entity.WatchItem;
import com.nbxapp.creepersvr.service.PriceParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liuguoping
 * @Description
 * @project apm service
 * @email liuguoping981@jk.cn
 * @date 2018/9/9
 */
@Component
public class TroyTimer {
    private static final Logger log = LoggerFactory.getLogger(TroyTimer.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Scheduled(cron = "0 0 0/6 * * ? ")
    private void performTroyMonitorTimer() {
        //!< 从troy服务器获取部分数据，
        log.info("update price：" + new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()));

        new PriceParserService().fetchAllPrice(jdbcTemplate);

    }
}
