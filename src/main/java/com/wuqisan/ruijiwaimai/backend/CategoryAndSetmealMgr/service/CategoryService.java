package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Category;


public interface CategoryService  extends IService<Category> {
    /**
     * 根据ID删除分类，删除之前需要判断是否关联菜品
     * @param id
     */
    public void remove(Long id);
}
