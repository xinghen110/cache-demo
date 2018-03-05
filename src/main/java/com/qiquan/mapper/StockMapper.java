package com.qiquan.mapper;

import com.qiquan.model.Stock;

import java.util.List;

public interface StockMapper {
//    int deleteByPrimaryKey(String symbol);
//
//    int insert(Stock record);
//
//    int insertSelective(Stock record);
//
//    Stock selectByPrimaryKey(String symbol);
//
//    int updateByPrimaryKeySelective(Stock record);
//
//    int updateByPrimaryKey(Stock record);

    //获取所有股票代码
    List<Stock> selectAllStock();
}