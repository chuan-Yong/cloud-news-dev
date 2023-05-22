package com.cloud.admin.service;

import com.cloud.entity.Category;

import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:58 2023/5/22
 * @Modified by:ycy
 */
public interface CategoryMngService {
    /**
     * 新增文章分类
     */
    public void createCategory(Category category);

    /**
     * 修改文章分类列表
     */
    public void modifyCategory(Category category);

    /**
     * 查询分类名是否已经存在
     */
    public boolean queryCatIsExist(String catName, String oldCatName);

    /**
     * 获得文章分类列表
     */
    public List<Category> queryCategoryList();
}
