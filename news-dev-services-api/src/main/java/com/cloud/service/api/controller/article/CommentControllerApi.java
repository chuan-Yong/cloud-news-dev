package com.cloud.service.api.controller.article;

import com.cloud.bo.CommentReplyBO;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 17:14 2023/5/24
 * @Modified by:ycy
 */
@Api(value = "评论相关业务的controller", tags = {"评论相关业务的controller"})
@RequestMapping("comment")
public interface CommentControllerApi {

    @PostMapping("createComment")
    @ApiOperation(value = "用户评论", notes = "用户评论", httpMethod = "POST")
    GraceJSONResult createArticle(@RequestBody @Valid CommentReplyBO commentReplyBO,
                                         BindingResult result);

    @GetMapping("counts")
    @ApiOperation(value = "用户评论数查询", notes = "用户评论数查询", httpMethod = "GET")
    GraceJSONResult commentCounts(@RequestParam String articleId);

    @GetMapping("list")
    @ApiOperation(value = "查询文章的所有评论列表", notes = "查询文章的所有评论列表", httpMethod = "GET")
    GraceJSONResult list(@RequestParam String articleId,
                                @RequestParam Integer page,
                                @RequestParam Integer pageSize);

    @PostMapping("mng")
    @ApiOperation(value = "查询我的评论管理列表", notes = "查询我的评论管理列表", httpMethod = "POST")
    GraceJSONResult mng(@RequestParam String writerId,
                               @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                               @RequestParam Integer page,
                               @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                               @RequestParam Integer pageSize);


    @PostMapping("/delete")
    @ApiOperation(value = "作者删除评论", notes = "作者删除评论", httpMethod = "POST")
    GraceJSONResult delete(@RequestParam String writerId,
                                  @RequestParam String commentId);
}
