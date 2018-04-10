package com.qiquan.service;

import com.qiquan.model.Limited;

import java.util.List;

public interface LimitedService {

    /**
     * 获取所有涨停数据
     * */
    List<Limited> getAll();

    /**
     * 获取所有涨停数据
     * */
    Limited getBySymbol(String symbol);

    /**
     * 更新涨停数据
     * */
    Limited update(Limited recode);


    /**
     * 增加涨停数据
     * */
    Limited insert(Limited recode);

    /**
     * 增加涨停数据
     * */
    Limited delete(Limited recode);
}
