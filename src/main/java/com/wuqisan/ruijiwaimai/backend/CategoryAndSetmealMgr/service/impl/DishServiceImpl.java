package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.mapper.DishMapper;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Dish;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.DishFlavor;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.DishFlavorService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.DishService;
import com.wuqisan.ruijiwaimai.dto.DishDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;
    /**
     * 新增菜品同时保存口味数据
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //1.保存菜品基本数据到数据库-菜品表
        this.save(dishDto);
        //2.保存菜品口味到口味表
        //2.1 给dishflavor 赋值 ①dishId ②
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }
        //2.2保存到菜品口味表
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    /**
     * 根据ID查询菜品和口味信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //1.从dish查询菜品的基本信息
        Dish dish = this.getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //2.从flavor表查口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        dishDto.setFlavors(dishFlavors);
        return dishDto;
    }

    /**
     * 更新菜品和口味信息
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //1.更新dish表基本信息
        updateById(dishDto);//因为继承关系，所以没关系哒
        //2.删除口味  小窍门：先删后加
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.removeById(dishDto.getId());
        //3.保存口味
        //3.1封装dishId
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(flavors);

    }
}
