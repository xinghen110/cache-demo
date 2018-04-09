package com.qiquan.service.impl;

import com.qiquan.mapper.LimitedMapper;
import com.qiquan.model.Limited;
import com.qiquan.service.LimitedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("limitedService")
public class LimitedServiceImpl implements LimitedService{

    @Autowired
    private LimitedMapper limitedMapper;

    @Override
    public List<Limited> getAll() {
        return limitedMapper.selectAll();
    }

    @Override
    public Limited update(Limited recode) {
        limitedMapper.updateByPrimaryKey(recode);
        return recode;
    }

    @Override
    public Limited insert(Limited recode) {
        limitedMapper.insert(recode);
        return recode;
    }
}
