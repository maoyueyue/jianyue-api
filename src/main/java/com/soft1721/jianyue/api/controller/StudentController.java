package com.soft1721.jianyue.api.controller;

import com.github.pagehelper.PageHelper;
import com.soft1721.jianyue.api.entity.User;
import com.soft1721.jianyue.api.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/mybaits")
public class StudentController {

    @Resource
    private UserService userService;
       @RequestMapping("/selectAll")
    public List<User> selectAll(int page, int size) {
        //第一参数：第几页。 第二参数：每页几条。基于拦截器模式直接使用即可。
        PageHelper.startPage(page,size);
        return userService.selectAll();
    }
}