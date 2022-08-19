package com.fly.store.service;

import com.fly.store.pojo.User;
import com.fly.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void addUser(){

        try {
            User user = new User();
            user.setUsername("fei");
            user.setPassword("321");
            Integer ret = userService.addUser(user);
            System.out.println(ret);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }

}
