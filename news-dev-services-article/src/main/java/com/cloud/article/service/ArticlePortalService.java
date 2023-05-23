package com.cloud.article.service;

import com.cloud.entity.Article;
import com.cloud.utils.PagedGridResult;

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
}
