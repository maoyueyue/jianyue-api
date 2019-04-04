package com.soft1721.jianyue.api.service.impl;

import com.soft1721.jianyue.api.entity.User;
import com.soft1721.jianyue.api.entity.dto.UserDTO;
import com.soft1721.jianyue.api.service.UserService;
import com.soft1721.jianyue.api.util.StatusConst;
import com.soft1721.jianyue.api.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Test
    public void getUserByMobile() {
        User user = userService.getUserByMobile("18136657760");
        System.out.println(user);
    }

    @Test
    public void signIn() {
        UserDTO loginUser = new UserDTO();
        loginUser.setMobile("18136657760");
        String base64Pass = StringUtil.getBase64Encoder("111");
        loginUser.setPassword(base64Pass);
        int status = userService.signIn(loginUser);
        assertEquals(StatusConst.SUCCESS, status);
    }

    @Test
    public void updateUser() {
        User user=userService.getUserById(1);
        user.setAvatar("https://upload.jianshu.io/users/upload_avatars/6597489/1a428417-2b5c-4307-89dc-40d6aa7651f1.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        userService.updateUser(user);
    }
}