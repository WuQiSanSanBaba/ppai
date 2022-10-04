package com.wuqisan.ruijiwaimai.dto;

import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Dish;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
