package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Setmeal;
import com.wuqisan.ruijiwaimai.dto.SetmealDto;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐：同时保存套餐和菜品信息
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐和菜品关联关系
     * @param ids
     */
    public void romoveWithDish(List<Long> ids);
}
