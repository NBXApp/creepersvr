package com.nbxapp.creepersvr.service;

import com.nbxapp.creepersvr.entity.WatchItem;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PriceParserService {

    private static final Logger log = LoggerFactory.getLogger(PriceParserService.class);

    private final static long ONE_DAY = 86400000l;
    private final static long startTick = 1530288000000l;//20180630

    private final static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    JdbcTemplate jdbc;

    public void fetchAllPrice(JdbcTemplate jdbcTemplate){

        jdbc = jdbcTemplate;

        new Thread(){
            public void run(){

                String sql = "SELECT * from watchList";
                List<WatchItem> list= jdbc.query(sql,new WatchItem(),new Object[]{});

                for(WatchItem watchItem: list){
                    if(watchItem.getSiteId().compareToIgnoreCase("gnc") == 0){
                        parseGNCHttpApiLog(jdbc, watchItem.getProductName(), watchItem.getUrl());
                    }
                }
            }
        }.start();
    }

    private int parseGNCHttpApiLog(JdbcTemplate jdbcTemplate, String productName, String url) {

        String lineString;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String price = null;
        String prompt1 = null;
        String prompt2 = null;
        String prompt3 = null;

        int total_rows = 0;
        try {
            Response response = okHttpClient.newCall(request).execute();

            BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            while ((lineString = br.readLine()) != null) {
                if(lineString.contains("dwData") && lineString.contains("var")){
                    //log.info("lineString: " + lineString);
                }else if(lineString.contains("price-sales")){
                    //log.info("lineString: " + lineString);
                    int pos1 = lineString.indexOf("price-sales");
                    String temp = lineString.substring(pos1);

                    int left = temp.indexOf(">");
                    int right = temp.indexOf("<");

                    price = temp.substring(left + 1, right);
                    log.info("price: " + price);
                }else if(lineString.contains("callout-message")){
                    //log.info("lineString: " + lineString);

                    int pos1 = lineString.indexOf("callout-message");
                    String temp = lineString.substring(pos1);

                    int left = temp.indexOf(">");
                    int right = temp.indexOf("<");

                    String callout = temp.substring(left + 1, right);

                    if (callout == null || callout.length() == 0) {
                        continue;
                    }

                    if (prompt1 == null) {
                        prompt1 = callout;
                        log.info("callout-message: " + prompt1);
                    } else if (prompt2 == null) {
                        prompt2 = callout;
                        log.info("callout-message: " + prompt2);
                    } else if (prompt3 == null) {
                        prompt3 = callout;
                        log.info("callout-message: " + prompt3);
                    }
                }
            }

            if(price != null && price.length() > 0){
                StringBuilder sb = new StringBuilder();

                sb.append("INSERT INTO priceHistory (productName,price,promopt1,promopt2,promopt3,gmtCreate) VALUES ('");
                sb.append(productName);
                sb.append("','");
                sb.append(price);
                sb.append("','");

                if(prompt1 != null){
                    sb.append(prompt1);
                }
                sb.append("','");

                if(prompt2 != null){
                    sb.append(prompt2);
                }
                sb.append("','");

                if(prompt3 != null){
                    sb.append(prompt3);
                }
                sb.append("','");

                sb.append(sf.format(new Date()));
                sb.append("')");

                jdbcTemplate.update(sb.toString());

            }

            return total_rows;
        } catch (Exception ex) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info(sf.format(new Date()) + ", task fail " + url);
        }
        return 0;
    }

}
