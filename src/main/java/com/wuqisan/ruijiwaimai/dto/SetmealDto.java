package com.wuqisan.ruijiwaimai.dto;


import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Setmeal;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
