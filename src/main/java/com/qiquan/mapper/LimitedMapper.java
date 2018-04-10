package com.qiquan.mapper;

import com.qiquan.model.Limited;

import java.util.List;

public interface LimitedMapper {

    int insert(Limited record);

    List<Limited> selectAll();

    int updateByPrimaryKey(Limited record);

    Limited selectBySymbol(String symbol);

    int deleteBySymbol(String symbol);
}