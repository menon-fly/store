package com.fly.store.service;

import com.fly.store.pojo.Address;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

import java.util.List;

public interface AddressService {

    /**
     * 增加收货地址
     * @param uid
     * @param username
     * @param address
     */
    void addAddress(Integer uid, String username, Address address);

    List<Address> queryByUid(Integer uid);

    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除选中地址
     * @param aid
     * @param uid
     * @param username
     */
    void delAddress(Integer aid,Integer uid,String username);

    Address queryByAid(Integer aid,Integer uid);
}
