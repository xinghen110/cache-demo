package com.qiquan.service;



public interface ZSDataService {

    /**
     * 通过股票代码获取股票最新交易数据
     *
     * @param   symbol  股票代码（如：sz000001）
     * @return  ZSData  最新交易数据
     * */

    void getZSDate( String symbol);
}