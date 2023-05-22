package com.cloud.admin.controller;

import com.cloud.admin.service.CategoryMngService;
import com.cloud.bo.SaveCategoryBO;
import com.cloud.entity.Category;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.admin.CategoryMngControllerApi;
import com.cloud.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:57 2023/5/22
 * @Modified by:ycy
 */
@RestController
public class CategoryMngController extends BaseController implements CategoryMngControllerApi {

    final static Logger logger = LoggerFactory.getLogger(CategoryMngController.class);

    @Resource
    private CategoryMngService categoryService;

    @Override
    public GraceJSONResult saveOrUpdateCategory(SaveCategoryBO saveCategoryBO, BindingResult result) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, Object> errorMap = getErrors(result);
            return GraceJSONResult.errorMap(errorMap);
        }

        Category newCat = new Category();
        BeanUtils.copyProperties(saveCategoryBO, newCat);
        // id为空新增，不为空修改
        if (saveCategoryBO.getId() == null) {
            // 查询新增的分类名称不能重复存在
            boolean isExist = categoryService.queryCatIsExist(newCat.getName(), null);
            if (!isExist) {
                // 新增到数据库
                categoryService.createCategory(newCat);
            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.CATEGORY_EXIST_ERROR);
            }
        } else {
            // 查询修改的分类名称不能重复存在
            boolean isExist = categoryService.queryCatIsExist(newCat.getName(), saveCategoryBO.getOldName());
            if (!isExist) {
                // 修改到数据库
                categoryService.modifyCategory(newCat);
            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.CATEGORY_EXIST_ERROR);
            }
        }

        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult getCatList() {
        return GraceJSONResult.ok(categoryService.queryCategoryList());
    }

    @Override
    public GraceJSONResult getCats() {
        // 先从redis中查询，如果有，则返回，如果没有，则查询数据库库后先放缓存，放返回
        String allCatJson = redisOperator.get(REDIS_ALL_CATEGORY);

        List<Category> categoryList = null;
        if (StringUtils.isBlank(allCatJson)) {
            categoryList = categoryService.queryCategoryList();
            redisOperator.set(REDIS_ALL_CATEGORY, JsonUtils.objectToJson(categoryList));
        } else {
            categoryList = JsonUtils.jsonToList(allCatJson, Category.class);
        }

        return GraceJSONResult.ok(categoryList);
    }
}
