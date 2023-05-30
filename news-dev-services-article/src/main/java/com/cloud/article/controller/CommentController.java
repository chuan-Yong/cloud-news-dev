package com.cloud.article.controller;

import com.cloud.article.service.CommentService;
import com.cloud.bo.CommentReplyBO;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.article.CommentControllerApi;
import com.cloud.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 17:33 2023/5/24
 * @Modified by:ycy
 */
@RestController
public class CommentController extends BaseController implements CommentControllerApi {

    @Autowired
    private CommentService commentService;

    @Override
    public GraceJSONResult createArticle(@Valid CommentReplyBO commentReplyBO, BindingResult result) {
        //判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, Object> errorMap = getErrors(result);
            return GraceJSONResult.errorMap(errorMap);
        }

        // 1. 根据留言用户的id查询他的昵称，用于存入到数据表进行字段的冗余处理，从而避免多表关联查询的性能影响
        String userId = commentReplyBO.getCommentUserId();

        // 2. 发起restTemplate调用用户服务，获得用户侧昵称
        Set<String> idSet = new HashSet<>();
        idSet.add(userId);
        String nickname = getBasicUserList(idSet).get(0).getNickname();
        String face = getBasicUserList(idSet).get(0).getFace();

        // 3. 保存用户评论的信息到数据库
        commentService.createComment(commentReplyBO.getArticleId(),
                                    commentReplyBO.getFatherId(),
                                    commentReplyBO.getContent(),
                                    userId,
                                    nickname,
                                    face);

        return GraceJSONResult.ok();
    }


    @Override
    public GraceJSONResult commentCounts(String articleId) {
        //从redis获取文章阅读数
        Integer counts = getCountsFromRedis(REDIS_ARTICLE_COMMENT_COUNTS + ":" + articleId);
        return GraceJSONResult.ok(counts);
    }

    @Override
    public GraceJSONResult list(String articleId, Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult gridResult = commentService.queryArticleComments(articleId, page, pageSize);
        return GraceJSONResult.ok(gridResult);
    }

    @Override
    public GraceJSONResult mng(String writerId, Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult gridResult = commentService.queryWriterCommentsMng(writerId, page, pageSize);
        return GraceJSONResult.ok(gridResult);
    }

    @Override
    public GraceJSONResult delete(String writerId, String commentId) {
        commentService.deleteComment(writerId, commentId);
        return GraceJSONResult.ok();
    }
}
