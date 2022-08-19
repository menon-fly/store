package com.fly.store.mapper;

import com.fly.store.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest //标注为测试类，打包项目不会一起打包
@RunWith(SpringRunner.class) //启动这个单元测试类(单元测试类是不能运行的)，参数固定
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper; //爆红：idea有检测功能，接口不能直接创建Bean（动态代理来解决）
    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("fly");
        user.setPassword("123");
        user.setPhone("15119639499");
        user.setEmail("180706@qq.com");
        Integer ret = userMapper.insertUser(user);
        System.out.println(ret);
    }

    @Test
    public void selectByUsername(){
        User user = userMapper.selectByUsername("fly");
        System.out.println(user);
    }

}
