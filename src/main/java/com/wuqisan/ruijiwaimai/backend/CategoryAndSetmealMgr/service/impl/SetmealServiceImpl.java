package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.mapper.SetmealMapper;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Setmeal;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.SetmealDish;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.SetmealDishService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.SetmealService;
import com.wuqisan.ruijiwaimai.common.Exception.CustomException;
import com.wuqisan.ruijiwaimai.dto.SetmealDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐：同时保存套餐和菜品信息
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
       //1保存套餐基本信息，操作setmeal，执行insert操作
        save(setmealDto);
       //2保存套餐和菜品的关联信息，操作setmeal_dish
        //2.1给中间表复制套餐ID
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(item->{
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());
        //2.2向关联关系表插入数据
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐和菜品关联关系
     *
     * @param ids
     */
    @Override
    @Transactional
    public void romoveWithDish(List<Long> ids) {
        //1.查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        //1.1如果不能，抛出业务异常
        if (count>0)
        {
            throw new CustomException("套餐正在售卖，不能删除");
        }
        //2.如果可以，先删除套餐表数据
        this.removeByIds(ids);
        //3.删除关系表
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        this.setmealDishService.remove(lambdaQueryWrapper);
    }
}
