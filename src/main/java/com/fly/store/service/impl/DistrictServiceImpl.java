package com.fly.store.service.impl;

import com.fly.store.mapper.DistrictMapper;
import com.fly.store.pojo.District;
import com.fly.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> queryByParent(String parent) {
        List<District> districtList = districtMapper.selectByParent(parent);
        return districtList;
    }

    @Override
    public String queryByCode(String code) {
        return districtMapper.selectByCode(code);
    }
}
