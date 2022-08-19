package com.fly.store.service;

import com.fly.store.pojo.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTest {
    @Autowired
    private DistrictService districtService;
    @Test
    public void queryByParent(){
        List<District> list = districtService.queryByParent("86");
        for(District d : list){
            System.err.println(d);
        }
    }
}
