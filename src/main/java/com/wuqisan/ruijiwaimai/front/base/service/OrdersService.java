package com.wuqisan.ruijiwaimai.front.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuqisan.ruijiwaimai.front.base.pojo.Orders;

public interface OrdersService extends IService<Orders> {
    /**
     * 完成用户下单
     * @param orders
     */
    public void submit(Orders orders);
}
