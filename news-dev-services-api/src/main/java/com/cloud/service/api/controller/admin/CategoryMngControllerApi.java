package com.cloud.service.api.controller.admin;

import com.cloud.bo.SaveCategoryBO;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:56 2023/5/22
 * @Modified by:ycy
 */

@Api(value = "文章分类维护", tags = {"文章分类维护controller"})
@RequestMapping("categoryMng")
public interface CategoryMngControllerApi {

    @PostMapping("saveOrUpdateCategory")
    @ApiOperation(value = "新增或修改分类", notes = "新增或修改分类", httpMethod = "POST")
     GraceJSONResult saveOrUpdateCategory(@RequestBody @Valid SaveCategoryBO newCategoryBO,
                                                BindingResult result);

    @PostMapping("getCatList")
    @ApiOperation(value = "查询分类列表", notes = "查询分类列表", httpMethod = "POST")
     GraceJSONResult getCatList();

    @GetMapping("getCats")
    @ApiOperation(value = "用户端查询分类列表", notes = "用户端查询分类列表", httpMethod = "GET")
    GraceJSONResult getCats();
}