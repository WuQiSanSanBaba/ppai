package com.wuqisan.ruijiwaimai.front.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuqisan.ruijiwaimai.common.Utils.BaseContext;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import com.wuqisan.ruijiwaimai.front.base.pojo.ShoppingCart;
import com.wuqisan.ruijiwaimai.front.base.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 处理购物车
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("收到的信息为:{}", shoppingCart);
        //1 设置用户ID
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        //2 查询菜品或者套餐 是否在购物车中
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        //2.1 是菜品
        if (shoppingCart.getDishId() != null) {
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            ShoppingCart one = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
            //2.1.1 查询表里有重复吗，有重复数量+1
            if (one != null) {
                shoppingCart.setId(one.getId());
                shoppingCart.setNumber(one.getNumber() + 1);
                shoppingCartService.updateById(shoppingCart);
            }
            //2.1.2 没有，插入
            else {
                shoppingCart.setNumber(1);
                shoppingCartService.save(shoppingCart);
            }
        }
        //2.2 是套餐
        else {
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            ShoppingCart one = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
            //2.2.1 查询表里有重复吗，有重复数量+1
            if (one != null) {
                shoppingCart.setId(one.getId());
                shoppingCart.setNumber(one.getNumber() + 1);
                shoppingCartService.updateById(shoppingCart);
            }
            //2.2.2 没有，插入
            else {
                shoppingCart.setNumber(1);
                shoppingCartService.save(shoppingCart);
            }
        }

        //2.2 如果不存在，则添加到购物车，数量默认是1
        return R.success(shoppingCart);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> condtion = new LambdaQueryWrapper<>();
        condtion.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        condtion.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(condtion);
        return R.success(shoppingCartList);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean()
    {
        log.info("用户清除购物车了捏");
        LambdaQueryWrapper<ShoppingCart> condition=new LambdaQueryWrapper<>();
        condition.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        this.shoppingCartService.remove(condition);
        return R.success("清空成功");
    }

    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        log.info("用户删购物车了捏：{}",shoppingCart);
        LambdaQueryWrapper<ShoppingCart> condition=new LambdaQueryWrapper<>();
        condition.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        //1.清除菜品
        if (shoppingCart.getDishId()!=null){
            condition.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }
        //2.清除套餐
        else {
            condition.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        this.shoppingCartService.remove(condition);
        return R.success("删除成功");
    }
}
