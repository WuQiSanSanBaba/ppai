package com.wuqisan.ruijiwaimai.front.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.common.Exception.CustomException;
import com.wuqisan.ruijiwaimai.common.Utils.BaseContext;
import com.wuqisan.ruijiwaimai.front.base.mapper.OrdersMapper;
import com.wuqisan.ruijiwaimai.front.base.pojo.AddressBook;
import com.wuqisan.ruijiwaimai.front.base.pojo.OrderDetail;
import com.wuqisan.ruijiwaimai.front.base.pojo.Orders;
import com.wuqisan.ruijiwaimai.front.base.pojo.ShoppingCart;
import com.wuqisan.ruijiwaimai.front.base.service.AddressBookService;
import com.wuqisan.ruijiwaimai.front.base.service.OrderDetailService;
import com.wuqisan.ruijiwaimai.front.base.service.OrdersService;
import com.wuqisan.ruijiwaimai.front.base.service.ShoppingCartService;
import com.wuqisan.ruijiwaimai.front.login.pojo.User;
import com.wuqisan.ruijiwaimai.front.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    UserService userService;
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    OrderDetailService orderDetailService;

    /**
     * 完成用户下单
     *
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        //1 获取当前用户Id
        Long userId = BaseContext.getCurrentId();
        //2 获取购物车信息
        LambdaQueryWrapper<ShoppingCart> shoppingCartCondition=new LambdaQueryWrapper<>();
        shoppingCartCondition.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartCondition);
        if (shoppingCartList==null || shoppingCartList.size()==0)
        {
            throw new CustomException("购物车为空不能下单");
        }
        //2.1 处理子订单表
        long orderId = IdWorker.getId();
        AtomicInteger amount=new AtomicInteger();
        List<OrderDetail> details = shoppingCartList.stream().map(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        //2.1 查询用户信息
        User user = userService.getById(userId);
        //2.2查地址信息
        LambdaQueryWrapper<AddressBook> addressBookCondition=new LambdaQueryWrapper<>();
        addressBookCondition.eq(AddressBook::getId,orders.getAddressBookId());
        AddressBook addressBook = addressBookService.getOne(addressBookCondition);
        if (addressBook==null)
        {
            throw new CustomException("地址信息有误不能下单");
        }
        //3 下单
        //3.1 主订单插入一条
        orders.setId(orderId);
        orders.setNumber(String.valueOf(IdWorker.getId()));
        orders.setStatus(2);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setPhone(addressBook.getPhone());
        orders.setUserName(user.getName());
        orders.setUserId(userId);
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);
        //3.2 子订单可能插入多条
        this.orderDetailService.saveBatch(details);
        //4 清空购物车
        this.shoppingCartService.remove(shoppingCartCondition);
    }
}
