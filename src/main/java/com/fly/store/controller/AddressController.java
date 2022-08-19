package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.pojo.Address;
import com.fly.store.service.AddressService;
import com.fly.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController{
    @Autowired
    private AddressService addressService;
    @Autowired
    private DistrictService districtService;

    @RequestMapping("/addAddress")
    public JsonResult<Void> addAddress(Address address, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addAddress(uid,username,address);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("/showAddress")
    public JsonResult<List<Address>> queryByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.queryByUid(uid);
        return new JsonResult<>(SUCCESS,list);
    }

    @RequestMapping("/setDefault")
    public JsonResult<Void> setDefault(Integer aid,HttpSession session){
        addressService.setDefault(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("/deleteAddress")
    public JsonResult<Void> deleteAddress(Integer aid,HttpSession session){
        addressService.delAddress(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(SUCCESS);
    }

}
