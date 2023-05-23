package com.cloud.article.controller;

import com.cloud.article.service.ArticlePortalService;
import com.cloud.entity.Article;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.article.ArticlePortalControllerApi;
import com.cloud.utils.JsonUtils;
import com.cloud.utils.PagedGridResult;
import com.cloud.vo.AppUserVo;
import com.cloud.vo.IndexArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:13 2023/5/23
 * @Modified by:ycy
 */
@RestController
public class ArticlePortalController extends BaseController implements ArticlePortalControllerApi {

    final static Logger logger = LoggerFactory.getLogger(ArticlePortalController.class);

    @Autowired
    private ArticlePortalService articlePortalService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GraceJSONResult list(String keyword,
                                Integer category,
                                Integer page,
                                Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = articlePortalService.queryIndexArticleList(keyword,
                                                                                category,
                                                                                page,
                                                                                pageSize);
        gridResult = rebuildArticleGrid(gridResult);
        return GraceJSONResult.ok(gridResult);
    }

    private PagedGridResult rebuildArticleGrid(PagedGridResult gridResult) {
        List<Article> list = (List<Article>)gridResult.getRows();
        // 1. 构建发布者id列表
        Set<String> idSet = new HashSet<>();
        List<String> idList = new ArrayList<>();
        for (Article a : list) {
            // 1.1 构建发布者的set
            idSet.add(a.getPublishUserId());
            // 1.2 构建文章id的list
            //idList.add(REDIS_ARTICLE_READ_COUNTS + ":" + a.getId());
        }
        System.out.println(idSet.toString());
        // 发起redis的mget批量查询api，获得对应的值
        //List<String> readCountsRedisList = redisOperator.mget(idList);

        List<AppUserVo> publisherList = getPublisherList(idSet);

        // 3. 拼接两个list，重组文章列表
        List<IndexArticleVO> indexArticleList = new ArrayList<>();
        for (int i = 0 ; i < list.size() ; i ++) {
            IndexArticleVO indexArticleVO = new IndexArticleVO();
            Article a = list.get(i);
            BeanUtils.copyProperties(a, indexArticleVO);

            // 3.1 从publisherList中获得发布者的基本信息
            AppUserVo publisher  = getUserIfPublisher(a.getPublishUserId(), publisherList);
            indexArticleVO.setPublisherVO(publisher);

            // 3.2 重新组装设置文章列表中的阅读量
           // String redisCountsStr = readCountsRedisList.get(i);
            int readCounts = 0;
            // if (StringUtils.isNotBlank(redisCountsStr)) {
            //     readCounts = Integer.valueOf(redisCountsStr);
            // }
            // indexArticleVO.setReadCounts(readCounts);

            indexArticleList.add(indexArticleVO);
        }
        gridResult.setRows(indexArticleList);
        // END
        return gridResult;
    }

    private List<AppUserVo> getPublisherList(Set idSet) {
        //远程调佣services-user服务中的接口
        String userServerUrlExecute
                = "http://localhost:8003/user/queryByIds?userIds=" + JsonUtils.objectToJson(idSet);
        ResponseEntity<GraceJSONResult> responseEntity
                = restTemplate.getForEntity(userServerUrlExecute, GraceJSONResult.class);
        GraceJSONResult bodyResult = responseEntity.getBody();
        List<AppUserVo> publisherList = null;
        assert bodyResult != null;
        if (bodyResult.getStatus() == 200) {
            String userJson = JsonUtils.objectToJson(bodyResult.getData());
            publisherList = JsonUtils.jsonToList(userJson, AppUserVo.class);
        }
        return publisherList;
    }

    private AppUserVo getUserIfPublisher(String publisherId,
                                         List<AppUserVo> publisherList) {
        for (AppUserVo user : publisherList) {
            if (user.getId().equalsIgnoreCase(publisherId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public GraceJSONResult hotList() {
       return GraceJSONResult.ok(articlePortalService.queryHotList());
    }

    @Override
    public GraceJSONResult queryArticleListOfWriter(String writerId,
                                                    Integer page,
                                                    Integer pageSize) {
        return null;
    }

    @Override
    public GraceJSONResult queryGoodArticleListOfWriter(String writerId) {
        return null;
    }

    @Override
    public GraceJSONResult detail(String articleId) {
        return null;
    }

    @Override
    public Integer readCounts(String articleId) {
        return null;
    }

    @Override
    public GraceJSONResult readArticle(String articleId, HttpServletRequest request) {
        return null;
    }
}
