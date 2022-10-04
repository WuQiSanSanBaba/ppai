package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.mapper.CategoryMapper;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Category;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Dish;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Setmeal;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.CategoryService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.DishService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.SetmealService;
import com.wuqisan.ruijiwaimai.common.Exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    /**
     * 根据ID删除分类，删除之前需要判断是否关联菜品
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        //1查询是否已经关联菜品或套餐，如果有关联，就抛出异常
        //1.1查菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int dishList = dishService.count(dishLambdaQueryWrapper);
        if (dishList>0)
        {
            //抛业务异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        //1.2查套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int setmealList = setmealService.count(setmealLambdaQueryWrapper);
        if (setmealList>0)
        {
            //抛业务异常
            throw new CustomException("当前分类关联了套餐，不能删除");

        }
        //2删除分类 正常删除
        super.removeById(id);
    }


}
