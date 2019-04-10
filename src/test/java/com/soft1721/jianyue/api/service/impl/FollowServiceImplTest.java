package com.soft1721.jianyue.api.service.impl;

import com.soft1721.jianyue.api.entity.Follow;
import com.soft1721.jianyue.api.service.FollowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowServiceImplTest {
    @Resource
    private FollowService followService;

    @Test
    public void getFollow() {
        System.out.println(followService.getFollow(1,2));
    }

    @Test
    public void getFollowsByUId() {
        System.out.println(followService.getFollowsByUId(1));
    }

    @Test
    public void insertFollow() {
        Follow follow= new Follow();
        follow.setFromUId(1);
        follow.setToUId(3);
        followService.insertFollow(follow);
    }

    @Test
    public void deleteFollow() {

    }
}