package com.nbxapp.creepersvr.controller;

import com.nbxapp.creepersvr.entity.HistoryPriceItem;
import com.nbxapp.creepersvr.entity.WatchItem;
import com.nbxapp.creepersvr.service.PriceParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping("/api/manual")
    public String manualStartForUpdate(){
        new PriceParserService().fetchAllPrice(jdbcTemplate);
        return new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]").format(new Date()) + ": 计算进行中";
    }

    @RequestMapping("/api/getWatchList")
    public List<WatchItem> getWatchList(){
        String sql = "SELECT * from watchList";
        List<WatchItem> list= jdbcTemplate.query(sql,new WatchItem(),new Object[]{});
        return list;
    }

    @RequestMapping("/api/getHistoryList")
    public List<HistoryPriceItem> getHistoryList(){
        String sql = "SELECT * from priceHistory";
        List<HistoryPriceItem> list= jdbcTemplate.query(sql,new HistoryPriceItem(),new Object[]{});
        return list;
    }

}
