package com.cloud.article.service.impl;

import com.cloud.article.mapper.CommentsMapper;
import com.cloud.article.mapper.CommentsMapperCustom;
import com.cloud.article.service.ArticlePortalService;
import com.cloud.article.service.CommentService;
import com.cloud.entity.Comments;
import com.cloud.service.BaseService;
import com.cloud.utils.PagedGridResult;
import com.cloud.vo.ArticleDetailVO;
import com.cloud.vo.CommentsVO;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 21:36 2023/5/24
 * @Modified by:ycy
 */
@Service
@Transactional
public class CommentServiceImpl extends BaseService implements CommentService {

    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private Sid sid;

    @Resource
    private ArticlePortalService articlePortalService;

    @Resource
    private CommentsMapperCustom commentsMapperCustom;

    @Override
    public void createComment(String articleId,
                              String fatherCommentId,
                              String content,
                              String userId,
                              String nickname,
                              String face) {
        String commentId = sid.nextShort();

        ArticleDetailVO article  = articlePortalService.queryDetail(articleId);

        Comments comments = new Comments();
        comments.setId(commentId);

        comments.setWriterId(article.getPublishUserId());
        comments.setArticleTitle(article.getTitle());
        comments.setArticleCover(article.getCover());
        comments.setArticleId(articleId);

        comments.setFatherId(fatherCommentId);
        comments.setCommentUserId(userId);
        comments.setCommentUserNickname(nickname);
        comments.setCommentUserFace(face);

        comments.setContent(content);
        comments.setCreateTime(new Date());

        commentsMapper.insert(comments);

        // 评论数累加
        redis.increment(REDIS_ARTICLE_COMMENT_COUNTS + ":" + articleId, 1);

    }

    @Override
    public PagedGridResult queryArticleComments(String articleId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        PageHelper.startPage(page, pageSize);
        List<CommentsVO> list = commentsMapperCustom.queryArticleCommentList(map);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult queryWriterCommentsMng(String writerId, Integer page, Integer pageSize) {
        Comments comment = new Comments();
        comment.setWriterId(writerId);

        PageHelper.startPage(page, pageSize);
        List<Comments> list = commentsMapper.select(comment);
        return setterPagedGrid(list, page);
    }

    @Override
    public void deleteComment(String writerId, String commentId) {
        Comments comment = new Comments();
        comment.setId(commentId);
        comment.setWriterId(writerId);

        commentsMapper.delete(comment);
    }
}
