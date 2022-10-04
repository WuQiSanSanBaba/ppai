package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Category;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Setmeal;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.SetmealDish;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.CategoryService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.SetmealDishService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.SetmealService;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import com.wuqisan.ruijiwaimai.dto.SetmealDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @Autowired
    SetmealDishService setmealDishService;
    @Autowired
    CategoryService categoryService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("新增套餐参数{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> pageInfoDto = new Page<>(page, pageSize);
        //1构造条件
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(name), Setmeal::getName, name);//name
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //2分页查询
        setmealService.page(pageInfo);
        //3对象拷贝 将setmeal中的属性赋给setmaelDto
        BeanUtils.copyProperties(pageInfo, pageInfoDto, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> setmealDtoList = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            //分类ID
            Long categoryId = item.getCategoryId();
            //查询分类名称
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        pageInfoDto.setRecords(setmealDtoList);
        return R.success(pageInfoDto);
    }

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        setmealService.romoveWithDish(ids);
        return R.success("删除成功了捏");
    }

    /**
     * 更改套餐状态 0禁售 1 起售
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status, @RequestParam("ids") List<Long> ids) {
        log.info("禁用套餐{}", ids);
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids);
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmealService.update(setmeal, lambdaQueryWrapper);
        return R.success("更新成功");
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        log.info("开始查询{}的详情套餐信息", id);
        //1 首先查到 setmeal
        Setmeal setmeal = this.setmealService.getById(id);
        //2 根据setmeal查询到 dish
        LambdaQueryWrapper<SetmealDish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> dishList = this.setmealDishService.list(dishLambdaQueryWrapper);
        //3 查询categoryname
        Category category = this.categoryService.getById(setmeal.getCategoryId());
        //4 封装起来
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        setmealDto.setSetmealDishes(dishList);

        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("修改套餐的信息为{}", setmealDto);
        // 1保存菜品信息
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        this.setmealDishService.remove(setmealDishLambdaQueryWrapper);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        this.setmealDishService.saveBatch(setmealDishes);
        // 2保存套餐信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        this.setmealService.updateById(setmeal);
        return R.success("成功裂捏");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> list( Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);
        return  R.success(list);
    }

}
