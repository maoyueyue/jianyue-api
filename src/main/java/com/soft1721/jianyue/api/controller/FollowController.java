package com.soft1721.jianyue.api.controller;

import com.soft1721.jianyue.api.entity.Follow;
import com.soft1721.jianyue.api.entity.vo.FollowVO;
import com.soft1721.jianyue.api.entity.vo.FollowedVO;
import com.soft1721.jianyue.api.service.FollowService;
import com.soft1721.jianyue.api.service.UserService;
import com.soft1721.jianyue.api.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/follow")
public class FollowController {
    @Resource
    private FollowService followService;
    @Resource
    private UserService userService;

    @PostMapping("/add")
    public ResponseResult followUser(@RequestParam("fromUId") int fromUId, @RequestParam("toUId") int toUId) {
        Follow follow = new Follow();
        follow.setFromUId(fromUId);
        follow.setToUId(toUId);
        followService.insertFollow(follow);
        return ResponseResult.success();
    }
    @PostMapping("/cancel")
    public ResponseResult cancelFollow(@RequestParam("fromUId") int fromUId, @RequestParam("toUId") int toUId) {
        followService.deleteFollow(fromUId, toUId);
        return ResponseResult.success();
    }
    @PostMapping("/one")
    public ResponseResult getOneFollow(@RequestParam("fromUId") int fromUId, @RequestParam("toUId") int toUId) {
        Follow follow=followService.getFollow(fromUId,toUId);
        return ResponseResult.success(follow);
    }
    @GetMapping(value = "/list")
    public ResponseResult getfollowlist(@RequestParam("fromUId") int fromUId) {
        List<FollowVO> followVOs=followService.getFollowsByUId(fromUId);
        return ResponseResult.success(followVOs);
    }

    @GetMapping(value = "/listed")
    public ResponseResult getFollowedlist(@RequestParam("toUId") int toUId) {
        List<FollowedVO> followedVOs=followService.getFollowedByToUId(toUId);
        return ResponseResult.success(followedVOs);
    }
}