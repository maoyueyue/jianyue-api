package com.soft1721.jianyue.api.controller;

import com.soft1721.jianyue.api.entity.Article;
import com.soft1721.jianyue.api.entity.Follow;
import com.soft1721.jianyue.api.entity.Img;
import com.soft1721.jianyue.api.entity.Like;
import com.soft1721.jianyue.api.entity.vo.ArticleVO;
import com.soft1721.jianyue.api.entity.vo.CommentVO;
import com.soft1721.jianyue.api.service.*;
import com.soft1721.jianyue.api.util.MsgConst;
import com.soft1721.jianyue.api.util.ResponseResult;
import com.soft1721.jianyue.api.util.StatusConst;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private CommentService commentService;
    @Resource
    private ImgService imgService;
    @Resource
    private FollowService followService;
    @Resource
    private LikeService likeService;

    @GetMapping(value = "/list")
    public ResponseResult getAll() {
        List<ArticleVO> articleList = articleService.selectAll();
        return ResponseResult.success(articleList);
    }
    @GetMapping(value = "/user")
    public ResponseResult getArticleByUId(@RequestParam("uId") int uId) {
        List<Article> articles =articleService.selectArticleByUid(uId);
        return ResponseResult.success(articles);
    }
    @GetMapping(value = "/{aId}")
    public ResponseResult getArticleById(@PathVariable("aId") int aId,@RequestParam("userId") int userId) {
        ArticleVO article = articleService.getArticleById(aId);
        int toUId = article.getUId();
        Map<String, Object> map = new HashMap<>();
            Follow follow = followService.getFollow(userId, toUId);
            if (follow != null) {
                map.put("followed", MsgConst.FOLLOWED);
            } else {
                map.put("followed", MsgConst.NO_FOLLOWED);
            }
            Like like = likeService.getLike(userId,aId);
        if (like != null) {
            map.put("liked", MsgConst.LIKED);
        } else {
            map.put("liked", MsgConst.NO_LIKED);
        }
        List<CommentVO> comments = commentService.selectCommentsByAId(aId);
        map.put("article", article);
        map.put("comments", comments);
        return ResponseResult.success(map);
    }
    @PostMapping("/add")
    public ResponseResult postArticle(@RequestParam("uId") int uId,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content) {
        Article article = new Article();
        article.setUId(uId);
        article.setTitle(title);
        article.setContent(content);
        article.setCreateTime(new Date());
        articleService.insertArticle(article);
        //新增文章后，将获取到的自增主键返回给客户端，方便图片地址的写入
        return ResponseResult.success(article.getId());
    }
    @GetMapping("/delete/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") int id){
        imgService.deleteImg(id);
        articleService.deleteArticleById(id);
        return ResponseResult.success();
    }
   /* @GetMapping(value = "/{aId}")
    public ResponseResult getArticleById(@PathVariable("aId") int aId) {
        ArticleVO article = articleService.getArticleById(aId);
        List<CommentVO> comments = commentService.selectCommentsByAId(aId);
        List<Img> imgs =imgService.selectImgsByAId(aId);
        Map<String, Object> map = new HashMap<>();
        map.put("article", article);
        map.put("comments", comments);
        map.put("imgs",imgs);
        return ResponseResult.success(map);
    }*/

}
