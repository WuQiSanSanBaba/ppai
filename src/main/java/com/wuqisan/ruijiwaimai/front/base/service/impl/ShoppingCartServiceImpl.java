package com.wuqisan.ruijiwaimai.front.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.common.Exception.CustomException;
import com.wuqisan.ruijiwaimai.common.Utils.BaseContext;
import com.wuqisan.ruijiwaimai.front.base.mapper.ShoppingCartMapper;
import com.wuqisan.ruijiwaimai.front.base.pojo.AddressBook;
import com.wuqisan.ruijiwaimai.front.base.pojo.Orders;
import com.wuqisan.ruijiwaimai.front.base.pojo.ShoppingCart;
import com.wuqisan.ruijiwaimai.front.base.service.AddressBookService;
import com.wuqisan.ruijiwaimai.front.base.service.ShoppingCartService;
import com.wuqisan.ruijiwaimai.front.login.pojo.User;
import com.wuqisan.ruijiwaimai.front.login.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {


}
