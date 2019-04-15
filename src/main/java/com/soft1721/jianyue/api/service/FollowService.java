package com.soft1721.jianyue.api.service;

import com.soft1721.jianyue.api.entity.Follow;
import com.soft1721.jianyue.api.entity.vo.FollowVO;
import com.soft1721.jianyue.api.entity.vo.FollowedVO;

import java.util.List;

public interface FollowService {
    Follow getFollow(int fromUId, int toUId);

    List<FollowVO> getFollowsByUId(int fromUId);

    List<FollowedVO> getFollowedByToUId(int toUId);

    void insertFollow(Follow follow);

    void deleteFollow(int fromUId, int toUId);
}