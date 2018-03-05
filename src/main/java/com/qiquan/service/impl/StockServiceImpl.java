package com.qiquan.service.impl;

import com.qiquan.mapper.StockMapper;
import com.qiquan.model.Stock;
import com.qiquan.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stockService")
@CacheConfig(cacheNames = "stockCache")
public class StockServiceImpl implements StockService{

    @Autowired
    private StockMapper stockMapper;

    /*
     * 获取所有股票数据
     * */
    @Override
    public List<Stock> selectAllStock() {
        return stockMapper.selectAllStock();
    }


    /*
     * 获取所有股票代码
     * */
    @Override
    @Cacheable
    public String getAllStock() {

        List<Stock> stocks = this.selectAllStock();
        StringBuilder builder = new StringBuilder(stocks.size()*9);

        for(Stock stock : stocks){
            builder.append(stock.getSymbolPrefix()+stock.getSymbol()+",");
        }

        return builder.toString();
    }
}
