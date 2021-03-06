package com.volcano.examonlineserv.controller;

import com.volcano.examonlineserv.bean.ArticleInfo;
import com.volcano.examonlineserv.bean.Comments;
import com.volcano.examonlineserv.bean.CommentsResponse;
import com.volcano.examonlineserv.config.Result;
import com.volcano.examonlineserv.config.ResultCode;
import com.volcano.examonlineserv.service.ArticleService;
import com.volcano.examonlineserv.utils.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取论坛文章列表
     * @return
     */
    @GetMapping("/api/v1/articles")
    public Result getArticles() {
        List<ArticleInfo> list = articleService.getArticles();
        return Result.getListResult(list);
    }

    /**
     * 获取论坛热榜文章列表
     * @return
     */
    @GetMapping("/api/v1/articles/hot")
    public Result getHotArticles() {
        List<ArticleInfo> list = articleService.getHotArticles();
        return Result.getListResult(list);
    }

    /**
     * 根据文章id获取评论列表
     * @param id
     * @return
     */
    @GetMapping("/api/v1/articles/comments")
    public Result getArticleComments(@RequestParam int id) {
        List<CommentsResponse> list = articleService.getArticleComments(id);
        return Result.getListResult(list);
    }

    /**
     * 发布文章
     * @param authorization
     * @param articleTmp
     * @return
     */
    @PostMapping("/api/v1/articles/edit")
    public Result uploadArticle(@RequestHeader String authorization, @RequestBody ArticleTmp articleTmp) {
        Result res;
        if(null == authorization || null == JwtUtil.validateToken(authorization)) {
            res = Result.failure(ResultCode.DATA_IS_WRONG);
            return res;
        }
        Integer id = JwtUtil.validateToken(authorization);
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setTitle(articleTmp.getTitle());
        articleInfo.setDescription(articleTmp.getDescription());
        if(articleTmp.getImg() != null) {
            articleInfo.setImg(articleTmp.getImg());
        }
        articleInfo.setField(articleTmp.getField());
        res = articleService.uploadArticle(id, articleInfo);
        return res;
    }

    /**
     * 搜索相关内容
     * @return
     */
    @GetMapping("/api/v1/articles/search")
    public Result searchArticle(@RequestParam String content) {
        List<ArticleInfo> list = articleService.searchArticle(content);
        return Result.getListResult(list);
    }

    @PostMapping("/api/v1/articles/uploadcomment")
    public Result uploadArticleComment(@RequestHeader String authorization, @RequestBody CommentBean commentBean) {
        Result res;
        if(null == authorization || null == JwtUtil.validateToken(authorization)) {
            res = Result.failure(ResultCode.DATA_IS_WRONG);
            return res;
        }
        Comments comments = new Comments();
        comments.setTargetid(commentBean.targetId); comments.setType(commentBean.type);
        comments.setDescription(commentBean.description);
        res = articleService.uploadArticleComment(JwtUtil.validateToken(authorization), comments);
        return res;
    }

    @PostMapping("/api/v1/articles/increaseZan")
    public Result increaseArticleZan(@RequestParam Integer articleId) {
        return articleService.increaseArticleZan(articleId);
    }

    @PostMapping("/api/v1/articles/decreaseZan")
    public Result decreaseArticleZan(@RequestParam Integer articleId) {
        return articleService.decreaseArticleZan(articleId);
    }
    @Data
    public static class ArticleTmp{

        private String title;

        private String description;

        private String img;

        private String field;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImg() {
            return img;
        }

        public String getField() {
            return field;
        }
    }

    @Data
    public static class CommentBean {
        Integer targetId;
        String type;
        String description;
        String img;
    }
}
