package com.cloud.article.mapper;

import com.cloud.entity.Article;
import com.cloud.service.mapper.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 21:41 2023/5/22
 * @Modified by:ycy
 */
@Repository
public interface ArticleMapperCustom extends MyMapper<Article> {

    void updateAppointToPublish();

}
