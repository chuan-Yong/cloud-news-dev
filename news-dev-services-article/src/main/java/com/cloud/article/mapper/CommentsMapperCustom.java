package com.cloud.article.mapper;

import com.cloud.entity.Comments;
import com.cloud.service.mapper.MyMapper;
import com.cloud.vo.CommentsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 17:37 2023/5/24
 * @Modified by:ycy
 */
@Repository
public interface CommentsMapperCustom extends MyMapper<Comments> {

    /**
     * 查询文章评论
     */
    List<CommentsVO> queryArticleCommentList(@Param("paramMap") Map<String, Object> map);

}
