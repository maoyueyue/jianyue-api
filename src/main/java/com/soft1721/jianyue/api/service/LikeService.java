package com.soft1721.jianyue.api.service;

import com.soft1721.jianyue.api.entity.Like;
import com.soft1721.jianyue.api.entity.vo.LikeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeService {
    Like getLike(@Param("uId") int uId, @Param("aId") int aId);

    void insertLike(Like like);

    void deleteLike(@Param("aId") int aId, @Param("uId") int uId);

    List<LikeVO> getLikeVOById (int uId);
}
