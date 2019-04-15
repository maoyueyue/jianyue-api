package com.soft1721.jianyue.api.mapper;

import com.soft1721.jianyue.api.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

public interface UserMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "mobile", column = "mobile"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "status", column = "status"),
            @Result(property = "regtime", column = "regtime"),
            @Result(property = "score", column = "score"),
            @Result(property = "token", column = "token")
    })
    @Select("SELECT * FROM t_user WHERE mobile = #{mobile} ")
    User getUserByMobile(String mobile);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "mobile", column = "mobile"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "status", column = "status"),
            @Result(property = "score", column = "score"),
            @Result(property = "regtime", column = "regtime"),
            @Result(property = "token", column = "token")
    })
    @Select("SELECT * FROM t_user WHERE id = #{id} ")
    User getUserById(int id);

    @Update("UPDATE t_user SET score=#{score},password=#{password},nickname=#{nickname},avatar=#{avatar},status=#{status},token=#{token} WHERE id =#{id}")
    void updateUser(User user);

    @Insert("INSERT INTO t_user(mobile,password,nickname,avatar,regtime,status)" +
            " VALUES(#{mobile}, #{password}, #{nickname},#{avatar},#{regtime},#{status}) ")
    void insert(User user);
}