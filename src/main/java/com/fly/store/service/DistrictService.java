package com.fly.store.service;


import com.fly.store.pojo.District;

import java.util.List;

public interface DistrictService {
    /**
     * 根据父代号查询子区域信息
     * @param parent
     * @return
     */
    List<District> queryByParent(String parent);

    String queryByCode(String code);
}
