package com.soft1721.jianyue.api.controller;

import com.soft1721.jianyue.api.entity.Like;
import com.soft1721.jianyue.api.entity.vo.LikeVO;
import com.soft1721.jianyue.api.service.LikeService;
import com.soft1721.jianyue.api.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/like")
public class LikeController {
    @Resource
    private LikeService likeService;


    @PostMapping("/add")
    public ResponseResult followUser(@RequestParam("aId") int aId, @RequestParam("uId") int uId) {
        Like like = new Like();
        like.setAId(aId);
        like.setUId(uId);
        likeService.insertLike(like);
        return ResponseResult.success();
    }

    @PostMapping("/cancel")
    public ResponseResult cancelFollow(@RequestParam("aId") int aId, @RequestParam("uId") int uId) {
        likeService.deleteLike(aId,uId);
        return ResponseResult.success();
    }
    @GetMapping(value = "/list")
    public ResponseResult getLikelist(@RequestParam("uId") int uId) {
        List<LikeVO> likeVOs=likeService.getLikeVOById(uId);
        return ResponseResult.success(likeVOs);
    }

}
