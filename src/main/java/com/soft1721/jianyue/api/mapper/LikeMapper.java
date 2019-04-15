package com.soft1721.jianyue.api.mapper;

import com.soft1721.jianyue.api.entity.Like;
import com.soft1721.jianyue.api.entity.vo.LikeVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LikeMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "aId", column = "a_id"),
            @Result(property = "uId", column = "u_id")
    })
    @Select("SELECT * FROM t_like WHERE u_id = #{uId} AND a_id = #{aId} ")
    Like getLike(@Param("uId") int uId, @Param("aId") int aId);

    @Results({
            @Result(property = "aId", column = "a_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "createTime",column = "create_time")
    })
    @Select("SELECT a.a_id,b.title,b.create_time,c.nickname FROM t_like a LEFT JOIN (t_article b LEFT JOIN t_user c ON b.u_id=c.id) ON a.a_id=b.id WHERE a.u_id =#{uId}")
    List<LikeVO> getLikeVOById (int uId);

    @Insert("INSERT INTO t_like (a_id,u_id) VALUES (#{aId},#{uId}) ")
    void insertLike(Like like);

    @Delete("DELETE  FROM t_like WHERE a_id = #{aId} AND u_id = #{uId} ")
    void deleteLike(@Param("aId") int aId, @Param("uId") int uId);
}
