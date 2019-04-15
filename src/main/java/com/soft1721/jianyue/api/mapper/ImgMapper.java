package com.soft1721.jianyue.api.mapper;

import com.soft1721.jianyue.api.entity.Img;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ImgMapper {
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "aId",column = "a_id"),
            @Result(property = "imgUrl",column = "img_url")
    })
    @Select("SELECT * FROM t_image WHERE a_id = #{aId} " )
    List<Img> selectImgsByAId(int aId);

    @Insert("INSERT INTO t_image(a_id,img_url) VALUES (#{aId},#{imgUrl})")
    void insertImg(Img img);

    @Delete("DELETE FROM t_image WHERE a_id = #{aId}")
    void deleteImg(int aId);
}
