package com.qiquan.controller;

import com.alibaba.fastjson.JSON;
import com.qiquan.job.StockJob;
import com.qiquan.model.ZSData;
import com.qiquan.service.StockService;
import com.qiquan.service.ZSDataService;
import com.qiquan.utils.ZSDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("unused")
@RestController
public class CacheController {

    private StockService stockService;
    private ZSDataService zsDataService;
    private StockJob stockJob;
    private ZSDataUtil zsDataUtil;

    @Autowired
    public CacheController(StockService stockService,
                           ZSDataService zsDataService,
                           StockJob stockJob,
                           ZSDataUtil zsDataUtil) {
        this.stockService = stockService;
        this.zsDataService = zsDataService;
        this.stockJob = stockJob;
        this.zsDataUtil = zsDataUtil;
    }

    @RequestMapping(value = "/test")
    public String testStock(){
        return stockService.getAllStock();
    }

    @RequestMapping(value = "/test1")
    public String testZSData(){
        zsDataService.getZSDate("sz000001,sz000002,sz000003");
        return "success!";
    }
    @RequestMapping(value = "/test2={code}")
    public String testZSData(@PathParam("code") String code){
        zsDataService.getZSDate(code);
        return "success!";
    }
    @RequestMapping(value = "/clear")
    public void clearZSData(){
        CacheManager cachemanager = zsDataUtil.getCachemanager();
        Cache zsData = cachemanager.getCache("ZSData");
        zsData.clear();
    }

    //每分钟运行一次数据采集任务
    @Scheduled(cron = "0 */1 * * * ?")
    public void testZSDataJob(){
        if(testTime()){     //交易时间进行数据采集
            stockJob.doJob();
        }
    }

    //每天凌晨1,2,3点清除缓存数据
    @Scheduled(cron = "0 0 1,2,3 * * ?")
    public void clearCache(){
        CacheManager cachemanager = zsDataUtil.getCachemanager();
        Cache zsData = cachemanager.getCache("ZSData");
        zsData.clear();
    }

    @RequestMapping(value = "/getZSData={code}", produces = {"application/json;charset=UTF-8"})
    public String testZSDataJob(@PathVariable("code") String code){
        CacheManager cachemanager = zsDataUtil.getCachemanager();
        Cache zsData = cachemanager.getCache("ZSData");
        Cache.ValueWrapper wrapper = zsData.get(code);
        List<ZSData> list = (List<ZSData>)wrapper.get();
        return JSON.toJSONString(list);
    }

    //判断当前是否是交易时间
    private boolean testTime(){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HHmm");
        String format = time.format(instance.getTime());
        int now = Integer.parseInt(format);

        if(928 < now && now < 1132 ){
            return true;
        }

        if(1258 < now && now < 1502 ){
            return true;
        }
        return false;
    }
}
