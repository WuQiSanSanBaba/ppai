package com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.pojo.Category;
import com.wuqisan.ruijiwaimai.backend.CategoryAndSetmealMgr.service.CategoryService;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     *新增分类
     * @param category
     * @return
     */
    @PostMapping()
    public  R<String> save(@RequestBody Category category){
       log.info("接收到的参数为{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //1.分页构造器
        Page<Category> categoryPage=new Page<>();
        //2.条件构造器
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper=new LambdaQueryWrapper<>();
        // 2.1添加排序条件
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
        // 3.分页查询
        categoryService.page(categoryPage,categoryLambdaQueryWrapper);
        return R.success(categoryPage);
    }

    /**
     *根据ID删除分离
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除的ID是{}", ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 根据Id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category)
    {
        log.info("更改的套餐是{}",category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list( Category category) {
        //1封装查询条件
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //1.1类型条件
        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //1.2排序条件，不加这个，sort有卵用
        lambdaQueryWrapper.orderByAsc(Category::getSort);
        lambdaQueryWrapper.orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);

    }


}
