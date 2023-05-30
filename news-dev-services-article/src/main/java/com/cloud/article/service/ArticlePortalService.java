package com.cloud.article.service;

import com.cloud.entity.Article;
import com.cloud.utils.PagedGridResult;
import com.cloud.vo.ArticleDetailVO;

import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:11 2023/5/23
 * @Modified by:ycy
 */
public interface ArticlePortalService {

    /**
     * 首页查询文章列表
     */
    PagedGridResult queryIndexArticleList(String keyword,
                                                 Integer category,
                                                 Integer page,
                                                 Integer pageSize);
    /**
     * 首页查询热闻列表
     */
    List<Article> queryHotList();

    /**
     * 查询作家发布的所有文章列表
     */
    PagedGridResult queryArticleListOfWriter(String writerId,
                                             Integer page,
                                             Integer pageSize);

    /**
     * 查询文章详情
     */
    ArticleDetailVO queryDetail(String articleId);

    /**
     * 作家页面查询近期佳文
     */
    PagedGridResult queryGoodArticleListOfWriter(String writerId);
}
