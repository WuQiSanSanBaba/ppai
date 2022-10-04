package com.wuqisan.ruijiwaimai.front.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuqisan.ruijiwaimai.common.Utils.BaseContext;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import com.wuqisan.ruijiwaimai.front.base.pojo.Orders;
import com.wuqisan.ruijiwaimai.front.base.service.OrdersService;
import com.wuqisan.ruijiwaimai.front.base.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    OrdersService ordersService;

    /**
     * 下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("有人下单了{}", orders);
        ordersService.submit(orders);
        return R.success("下单成功了捏");
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize) {
        Page<Orders> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        this.ordersService.page(page1, ordersLambdaQueryWrapper);
        return R.success(page1);
    }
}
