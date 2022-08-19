package com.fly.store.mapper;

import com.fly.store.pojo.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {
    /**
     * 插入用户收货地址
     * @param address
     * @return 影响数据行数
     */
    Integer insertAddress(Address address);

    /**
     * 根据用户id统计地址数量
     * @param uid
     * @return 当前用户收货地址数量
     */
    Integer countByUid(Integer uid);

    /**
     * 根据uid查询用户收货地址
     * @param uid
     * @return
     */
    List<Address> selectByUid(Integer uid);

    /**
     * 根据uid更新默认地址为非默认
     * @param uid
     * @return
     */
    Integer updateNotDefaultAddress(Integer uid);

    /**
     * 设置默认地址
     * @param aid
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateDefaultAddress(@Param("aid") Integer aid,
                                 @Param("modifiedUser") String modifiedUser,
                                 @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据aid查询地址
     * @param aid
     * @return
     */
    Address selectByAid(Integer aid);

    /**
     * 根据aid删除地址
     * @param aid
     * @return
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据uid查询用户最后修改的地址
     * @param uid
     * @return
     */
    Address selectLastModified(Integer uid);
}
