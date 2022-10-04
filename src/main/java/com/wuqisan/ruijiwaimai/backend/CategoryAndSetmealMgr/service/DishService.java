package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Dish;
import com.wuqisan.ruijiwaimai.dto.DishDto;

public interface DishService extends IService<Dish> {
    /**
     * 新增菜品同时保存口味数据
     */
    public void saveWithFlavor(DishDto dishDto);

    /**
     * 根据ID查询菜品和口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品和口味信息
     * @param dishDto
     */
    public void updateWithFlavor(DishDto dishDto);
}
