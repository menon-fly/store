package com.fly.store.mapper;

import com.fly.store.pojo.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根据父代码查询子区域
     * @param parent
     * @return
     */
    List<District> selectByParent(String parent);

    /**
     *
     * @param code
     * @return
     */
    String selectByCode(String code);
}
