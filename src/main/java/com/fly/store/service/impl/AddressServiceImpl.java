package com.fly.store.service.impl;

import com.fly.store.mapper.AddressMapper;
import com.fly.store.pojo.Address;
import com.fly.store.service.AddressService;
import com.fly.store.service.DistrictService;
import com.fly.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Value("${user.address.max-count}")
    private Integer maxCount;
    @Autowired
    private DistrictService districtService;
    @Override
    public void addAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount){
            throw new AddressCountLimitException("地址数量超出限制");
        }
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0; //1为默认
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        address.setProvinceName(districtService.queryByCode(address.getProvinceCode()));
        address.setAreaName(districtService.queryByCode(address.getAreaCode()));
        address.setCityName(districtService.queryByCode(address.getCityCode()));
        Integer rows = addressMapper.insertAddress(address);
        if (rows != 1){
            throw new InsertException("增加地址发生异常");
        }
    }

    @Override
    public List<Address> queryByUid(Integer uid) {
        return addressMapper.selectByUid(uid);
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.selectByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("地址不存在");
        }
        Integer rows = addressMapper.updateNotDefaultAddress(uid);
        if (rows < 1){
            throw new UpdateException("设置默认地址发生异常");
        }
        rows = addressMapper.updateDefaultAddress(aid,username,new Date());
        if (rows != 1){
            throw new UpdateException("设置默认地址发生异常");
        }
    }

    @Override
    public void delAddress(Integer aid, Integer uid, String username) {
        Address address = addressMapper.selectByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("地址不存在");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1){
            throw new DeleteException("删除地址发生异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0){
            return;
        }
        if (address.getIsDefault() == 0){
            return;
        }
        Address address1 = addressMapper.selectLastModified(uid);
        rows = addressMapper.updateDefaultAddress(address1.getAid(), username, new Date());
        if (rows != 1){
            throw new UpdateException("重置默认地址发生异常");
        }
    }

    @Override
    public Address queryByAid(Integer aid,Integer uid) {
        Address address = addressMapper.selectByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("地址不存在");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedTime(null);
        address.setModifiedUser(null);
        return address;
    }
}
