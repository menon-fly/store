package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.pojo.District;
import com.fly.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController{

    @Autowired
    private DistrictService districtService;

    @RequestMapping({"/",""})
    public JsonResult<List<District>> queryByParent(String parent){
        List<District> list = districtService.queryByParent(parent);
        return new JsonResult<>(SUCCESS,list);
    }
}
