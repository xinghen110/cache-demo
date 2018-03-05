package com.qiquan.service;

import com.qiquan.model.Stock;

import java.util.List;

public interface StockService {

    //获取所有股票数据
    List<Stock> selectAllStock();
    //获取所有股票代码
    String getAllStock();
}
