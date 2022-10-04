package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Category;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Dish;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.DishFlavor;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.CategoryService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.DishFlavorService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.DishService;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import com.wuqisan.ruijiwaimai.dto.DishDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("接收到的菜品信息为{}", dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //1.分页查询 1.1构造分页条件
        Page<Dish> pagedish = new Page<Dish>();
        //分页Dto对象
        Page<DishDto> dishDtoPage=new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //1.1.1根据名字查询
        dishLambdaQueryWrapper.like(name != null, Dish::getName, name);
        //1.1.2按更新时间排序倒序
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        //1.2分页查询塔塔开
        dishService.page(pagedish,dishLambdaQueryWrapper);
        //2对象拷贝 pagedish==》dishDtoPage
        //2.1排除record
        BeanUtils.copyProperties(pagedish,dishDtoPage,"records");
        //2.2通过stream流赋值
        List<Dish> dishList = pagedish.getRecords();
        List<DishDto> dishDtoList = dishList.stream().map(item -> {
            DishDto dishDto = new DishDto();
            Long categoryId = item.getCategoryId();//分类ID
            Category category = categoryService.getById(categoryId);//分类对象
            if (category!=null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            BeanUtils.copyProperties(item, dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        //2.3将获得的新list赋值给dishdto
        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
    }

    /**
     * 根据ID查询菜品的信息和对应的口味的信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        //直接返回
    return R.success(dishService.getByIdWithFlavor(id));
    }
    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info("接收到的菜品信息为{}", dishDto);
        dishService.updateWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

   /* *//**
     * 根据条件查询菜品
     * @param dish
     * @return
     *//*
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        //封装查询条件 1.ID 2. 排序{sort 、updatetime}3.状态为 1（启用的）
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort,Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(queryWrapper);
        return R.success(dishList);
    }*/

    /**
     * 根据条件查询菜品
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //1封装查询条件 1.ID 2. 排序{sort 、updatetime}3.状态为 1（启用的）
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort,Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(queryWrapper);
        //2通过stream流赋值 给dishdto赋值 包括口味和分类
        List<DishDto> dishDtoList = dishList.stream().map(item -> {
            DishDto dishDto = new DishDto();
            Long categoryId = item.getCategoryId();//分类ID
            Category category = categoryService.getById(categoryId);//分类对象
            if (category!=null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            BeanUtils.copyProperties(item, dishDto);
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper=new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> dishFlavors = this.dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtoList);
    }
}
