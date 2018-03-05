package com.qiquan.job;

import com.qiquan.service.StockService;
import com.qiquan.service.ZSDataService;
import com.qiquan.utils.ZSDataUtil;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("StockJob")
public class StockJob {

    private StockService stockService;
    private ZSDataService zsDataService;
    private ZSDataUtil zsDataUtil;

    public StockJob(StockService stockService,
                    ZSDataService zsDataService,
                    ZSDataUtil zsDataUtil) {
        this.stockService = stockService;
        this.zsDataService = zsDataService;
        this.zsDataUtil = zsDataUtil;
    }

    public void doJob(){
        String allStock = stockService.getAllStock();
        List<String> strings = zsDataUtil.splitStock(allStock);

        strings.forEach(item->zsDataService.getZSDate(item));

    }


}
