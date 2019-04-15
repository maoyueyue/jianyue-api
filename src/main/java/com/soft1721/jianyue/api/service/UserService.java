package com.soft1721.jianyue.api.service;

import com.soft1721.jianyue.api.entity.User;
import com.soft1721.jianyue.api.entity.dto.UserDTO;

public interface UserService {

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    User getUserByMobile(String mobile);

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */

    User getUserById(int id);

    /**
     * 登录
     *
     * @param userDTO
     * @return boolean
     */
    int signIn(UserDTO userDTO);

    /**
     * 更新用户信息
     *
     * @param user
     */
    void updateUser(User user);


    /**
     * 用户注册方法
     * @param userDTO
     * @return int
     */
    void signUp(UserDTO userDTO);
}